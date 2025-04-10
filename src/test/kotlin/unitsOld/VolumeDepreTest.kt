package unitsOld

class VolumeDepreTest: GenericUnitTest<VolumeUnit, VolumeDepre>(VolumeUnit.entries.toTypedArray(), Int::toVolume, Long::toVolume, Double::toVolume) {
}