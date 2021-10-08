import java.io.{Reader, StreamTokenizer}
import scala.collection.mutable.ListBuffer

object Compiler {
	class LexingException(message: String) extends Exception(message) {}
	
	def lex(reader: Reader): List[String] = {
		val streamTokenizer = new StreamTokenizer(reader)
		val tokens = new ListBuffer[String]()
		
		streamTokenizer.resetSyntax()
		streamTokenizer.whitespaceChars(0, 0x20)
		streamTokenizer.wordChars('0', '9')
		streamTokenizer.wordChars('-', '.')
		streamTokenizer.wordChars('+', '+')
		streamTokenizer.wordChars('a', 'z')
		streamTokenizer.wordChars('A', 'Z')
		streamTokenizer.wordChars(0xa0, 0xff)
		streamTokenizer.quoteChar('"')
		streamTokenizer.quoteChar('\'')
		streamTokenizer.slashSlashComments(true)
		streamTokenizer.slashStarComments(true)
		
		while (streamTokenizer.nextToken() != StreamTokenizer.TT_EOF) {
			streamTokenizer.ttype match {
				case StreamTokenizer.TT_WORD => {
					tokens += streamTokenizer.sval
				}
				case _ => throw new LexingException(
					s"Unrecognized token ${streamTokenizer.sval}"
				)
			}
		}
		
		tokens.toList
	}
	
	def parse(tokens: List[String]): AbstractSyntaxTree = {
		new AbstractSyntaxTree(tokens)
	}
}
