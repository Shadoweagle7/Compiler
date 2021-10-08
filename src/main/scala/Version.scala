class Version(majorVersion : Int, minorVersion : Int, releaseLevelVersion : String) {
	def major: Int = { majorVersion }
	def minor: Int = { minorVersion }
	def releaseLevel: String = { releaseLevelVersion }
	
	override def toString: String =
		s"${majorVersion}.${minorVersion}.${releaseLevelVersion}"
}
