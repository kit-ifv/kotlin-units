plugins {
    kotlin("jvm") version "2.2.0"
    id("maven-publish")
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
    mainClass.set("ArrayGenKt")
}

tasks.register("printSourceSetInformation"){
    description = "Prints all source sets with their directories"
    doLast{
        sourceSets.forEach { srcSet ->
            println("[${srcSet.name}]")
            println("-->Source directories: ${srcSet.allJava.srcDirs}")
            println("-->Output directories: ${srcSet.output.classesDirs.files}")
        }
    }
}

kotlin {
    jvmToolchain(21)
}
allprojects {
    apply(plugin = "maven-publish")

    project.group = "edu.kit.ifv.mobitopp"

    afterEvaluate {

        if (checkProperty("doPublish")) {
            /* mobiTopp publishing process (see .gitlab-ci.yml)
             * Parameters such as "doPublish" must be passed in gradle command:
             *  - ./gradlew <TASKS> publish -PdoPublish=true -Pparam=value...
             * Lookup of parameters doPublish and isRelease returns true if they are specified and their value reads "true".
             * Other required parameters must be specified, otherwise an error is thrown.
             *
             * The pipeline build version is used as the published artifacts version string.
             *  - uses parameter: "buildVersion"
             *
             * Every merge on main is published to local repo: see deploy-job
             *  - checks: doPublish=true, isRelease=false
             *  - requires parameters: "localUrl", "localRepoUser" and "localRepoPassword"
             *
             * Public releases must be published manually:
             *  - checks: doPublish=true, isRelease=true
             *  - requires parameters: "publicUrl", "publicRepoUser" and "publicRepoPassword"
             */

            project.version = requireProperty("buildVersion")
            println("Setup publishing configuration for ${group}:${project.name}:${version}.")

            publishing {

                publications {
                    register("mavenData", MavenPublication::class) {
                        from(components["kotlin"]) // For Kotlin projects
                        groupId = group.toString()
                        artifactId = project.name
                        version = project.version.toString()
                    }
                }

                repositories {
                    if (checkProperty("isRelease")) {
                        println("Activate: publish public release!")
                        println("WARNING: Public release still deactivated!")

                        //  Keep for first public release of reengineered mobitopp
                        //maven {
                        //    name = "PublicRepo"
                        //    url = uri(requireProperty("publicUrl"))
                        //    credentials {
                        //        username = requireProperty("publicRepoUser")
                        //        password = requireProperty("publicRepoPassword")
                        //    }
                        //}

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

    }

}

fun requireProperty(property: String, orElse: String? = null): String =
    requireNotNull(project.findProperty(property) as? String ?: orElse) {
        "Could not find property '$property'. Please check the gradle command args. It should contain:\n" +
                "    ./gradlew ... -P$property=<VALUE> ..."
    }

fun checkProperty(property: String): Boolean = project.hasProperty(property) && project.property(property) == "true"
