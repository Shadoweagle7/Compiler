object ArgumentParser {
	// -flag1 arg1 arg2 arg3 -flag2 arg1 arg2 -flag3 arg1
	
	private def parseImpl(args: List[String]) = {
		args match {
			case head :: tail =>
			case Nil =>
		}
	}
	
	def parse(args: Array[String]): Map[String, List[String]] = {
		null
	}

}
