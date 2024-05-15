package units

class VolumeTest: GenericUnitTest<VolumeUnit, Volume>(VolumeUnit.entries.toTypedArray(), Int::toVolume, Long::toVolume, Double::toVolume) {
}