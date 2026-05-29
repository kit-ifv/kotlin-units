plugins {
    kotlin("jvm") version "2.3.0"
    id("maven-publish")
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
    id("signing")
}
group = "edu.kit.ifv.mobitopp"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.register("cleanGeneratedArrays") {
    description = "Deletes all files which end with `Array.kt` in the " +
            "`src/main/kotlin/edu/kit/ifv/units/arrays/` folder."
    delete(fileTree("src/main/kotlin/edu/kit/ifv/units/arrays").matching {
        include("*Array.kt")
    })

}

sourceSets {
    create("codeGen") {
        kotlin.srcDirs("src/codeGenSource/kotlin")
    }
}


tasks.register<JavaExec>("generateArrays") {
    dependsOn("cleanGeneratedArrays")
    description = "Generates all type specific arrays like TemperatureArray, EnergyArray,... in " +
            "`src/main/kotlin/edu/kit/ifv/units/arrays/`"
    classpath = sourceSets.getByName("codeGen").runtimeClasspath
    mainClass.set("array/ArrayGenKt")
}

tasks.register("printSourceSetInformation"){
    description = "Prints all source sets with their directories"
    doLast{
        sourceSets.forEach { srcSet ->
            println("[${srcSet.name}]")
            println("-->Source directories: ${srcSet.allSource.srcDirs}")
            println("-->Output directories: ${srcSet.output.classesDirs.files}")
        }
    }
}

kotlin {
    jvmToolchain(25)
}


java {
    withJavadocJar()
    withSourcesJar()
}


if (checkProperty("doPublish")) {
    /* mobiTopp publishing process (see .gitlab-ci.yml)
        * Parameters such as "doPublish" must be passed in gradle command:
        *  - ./gradlew <TASKS> -PdoPublish=true -Pparam=value...
        * Lookup of parameters doPublish and isRelease returns true if they are specified and their value reads "true".
        * Other required parameters must be specified, otherwise an error is thrown.
        *
        * The pipeline build version is used as the published artifacts version string.
        *  - uses parameter: "buildVersion"
        *
        * Every merge on main is published to local repo: see deploy-job
        *  - checks: doPublish=true, isRelease=false
        *  - gradle task: publish
        *  - requires parameters: "localUrl", "localRepoUser" and "localRepoPassword"
        *
        * Public releases must be published manually:
        *  - checks: doPublish=true, isRelease=true
        *  - gradle tasks: publishToSonatype closeSonatypeStagingRepository
        *  - requires parameters: sonatypeUsername, sonatypePassword signing.keyId signing.password signing.secretKeyRingFile
        */

    project.version = requireProperty("buildVersion")
    println("Setup publishing configuration for ${group}:${project.name}:${version}.")

    val githubURL: String = "github.com/kit-ifv/kotlin-units"
    val projectDescription: String = "A collection of kotlin value classes for units of measurement. Uses zero-cost abstraction for type safety at compile-time and high performance at run-time."
    publishing {

        publications {
            create<MavenPublication>("mavenData") {
                from(components["java"])
                groupId = group.toString()
                artifactId = project.name
                version = project.version.toString()

                pom {
                    name.set(project.name)
                    description.set(projectDescription)
                    url.set("https://$githubURL")

                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://mit-license.org")
                        }
                    }

                    developers {
                        developer {
                            id.set("Robin Andre")
                            name.set("Robin Andre")
                            email.set("robin.andre@kit.edu")
                        }
                    }

                    scm {
                        connection.set("scm:git:git:https://$githubURL.git")
                        developerConnection.set("scm:git:ssh://git@$githubURL.git")
                        url.set("https://$githubURL")
                    }
                }
            }
        }

        repositories {
            if (checkProperty("isRelease")) {
                println("Activate: publish public release!")

                signing {
                    sign(publishing.publications)
                }

                nexusPublishing {
                    repositories {
                        // see https://central.sonatype.org/publish/publish-portal-ossrh-staging-api/#configuration
                        sonatype {
                            nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
                            snapshotRepositoryUrl.set(uri("https://central.sonatype.com/repository/maven-snapshots/"))
                        }
                    }
                }

            } else {
                println("Activate: publish local build!")
                maven {
                    name = "LocalRepo"
                    url = uri(requireProperty("localUrl"))
                    credentials {
                        username = requireProperty("localRepoUser")
                        password = requireProperty("localRepoPassword")
                    }
                }
            }
        }

    }

}


fun requireProperty(property: String, orElse: String? = null): String =
    requireNotNull(project.findProperty(property) as? String ?: orElse) {
        "Could not find property '$property'. Please check the gradle command args. It should contain:\n" +
                "    ./gradlew ... -P$property=<VALUE> ..."
    }

fun checkProperty(property: String): Boolean = project.hasProperty(property) && project.property(property) == "true"
