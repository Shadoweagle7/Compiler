object Main {
	val compilerName: String = "SE7C";
	val version = new Version(0, 1, "alpha")
	
	val logLevels: Array[String] = Array[String](
		"Trace",
		"Debug",
		"Info",
		"Warn",
		"Error",
		"Fatal",
		"Off"
	)
	
	def main(args: Array[String]): Unit = {
		if (args.length == 0) {
			println(
				s"SE7 Compiler v[${version.toString}]"
			)
			
			return
		}
		
		val arguments = ArgumentParser.parse(args)
	}
}
