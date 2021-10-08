import scala.collection.mutable.ListBuffer

class AbstractSyntaxTree(private val tokens: List[String]) {
	/*
	* E.g.
	*
	* int main(string[] args) {
	* 	return 0;
	* }
	*
	* */
	
	private abstract class Node(nodeName: String, nodeValue : Any, childNodes: Node*) {
		val name = nodeName
		val value = nodeValue
		val children = ListBuffer[Node](childNodes: _*)
	}
	
	private case object ProgramNode extends Node("SE7::Program", None)
	private case class IdentifierNode(nodeName: String) extends Node(nodeName, None)
	private case class OpenParenthesisNode() extends Node("(", None)
	private case class CloseParenthesisNode() extends Node("(", None)
	private case class OpenSquareBracketNode() extends Node("[", None)
	private case class CloseSquareBracketNode() extends Node("]", None)
	private case class OpenCurlyBracketNode() extends Node("{", None)
	private case class CloseCurlyBracketNode() extends Node("}", None)
	private abstract class KeywordNode(keywordName: String, childNodes: Node*)
		extends Node(keywordName, None, childNodes: _*)
	private case class ImportKeywordNode() extends KeywordNode("import")
	private case class ModuleKeywordNode() extends KeywordNode("module")
	private abstract class TypeNode(name: String) extends KeywordNode(name)
	private abstract class PrimitiveKeywordTypeNode(keywordName: String) extends TypeNode(keywordName)
	private abstract class IntegralPrimitiveTypeKeywordNode(keywordName: String)
		extends PrimitiveKeywordTypeNode(keywordName) {}
	private abstract class ModifierKeywordNode(keywordName: String) extends KeywordNode(keywordName)
	private case class UnsignedKeywordNode() extends ModifierKeywordNode("unsigned")
	private case class ConstKeywordNode() extends ModifierKeywordNode("const")
	private case class VolatileKeywordNode() extends ModifierKeywordNode("volatile")
	private case class MutableKeywordNode() extends ModifierKeywordNode("mutable")
	private case class BoolKeywordNode() extends PrimitiveKeywordTypeNode("bool")
	private case class CharKeywordNode()
		extends IntegralPrimitiveTypeKeywordNode("char")
	private case object WideCharKeywordNode extends PrimitiveKeywordTypeNode("wchar")
	private case class ShortKeywordNode()
		extends IntegralPrimitiveTypeKeywordNode("short")
	private case class IntKeywordNode()
		extends IntegralPrimitiveTypeKeywordNode("int")
	private case class LongKeywordNode()
		extends IntegralPrimitiveTypeKeywordNode("long")
	private case class LongLongKeywordNode()
		extends IntegralPrimitiveTypeKeywordNode("long long")
	private case class FloatKeywordNode() extends PrimitiveKeywordTypeNode("float")
	private case class DoubleKeywordNode() extends PrimitiveKeywordTypeNode("double")
	private case class LongDoubleKeywordNode() extends PrimitiveKeywordTypeNode("long double")
	
	private abstract class Expression(nodeValue: Any) extends Node("", nodeValue)
	
	private def clampCharMod(char: Char): Char = {
		val result = (char % 128).asInstanceOf[Char]
		
		if (result > 0) {
			return (-128 + result).asInstanceOf[Char]
		}
		
		result
	}
	
	private case class BoolExpression(bool: Boolean) extends Expression(bool)
	private case class CharExpression(char: Char, unsigned: Boolean) extends Expression(clampCharMod(char))
	private case class WideCharExpression(char: Char) extends Expression(char)
	private case class ShortExpression(short: Short, unsigned: Boolean) extends Expression(short)
	private case class IntExpression(int: Int, unsigned: Boolean) extends Expression(int)
	private case class StructExpression(unsigned: Boolean) extends Expression(\\)
	private case class ClassExpression(unsigned: Boolean) extends Expression(\\)
	
	private case class BlockExpressionNode(
									  openCurlyBracketNode: OpenCurlyBracketNode,
									  closeCurlyBracketNode: CloseCurlyBracketNode,
									  childNodes: Node*
								  ) extends Expression(None)
	
	private case class IfExpressionNode(
								   openParenthesisNode: OpenParenthesisNode,
								   condition: BoolExpression,
								   closeParenthesisNode: CloseParenthesisNode,
								   blockExpressionNode: BlockExpressionNode
							   ) extends KeywordNode(
		"if",
		openParenthesisNode,
		condition,
		closeParenthesisNode
	)
	
	private def createNode(name: String): Node = {
		name match {
			case "(" => OpenParenthesisNode()
			case ")" => CloseParenthesisNode()
			case "[" => OpenSquareBracketNode()
			case "]" => CloseSquareBracketNode()
			case "{" => OpenCurlyBracketNode()
			case "}" => CloseCurlyBracketNode()
			case "unsigned" => UnsignedKeywordNode()
			case "const" => UnsignedKeywordNode()
			case "volatile" => UnsignedKeywordNode()
			case "mutable" => UnsignedKeywordNode()
			case "bool" =>
			case "char" =>
			case "wchar" =>
			case "short" =>
			case "int" =>
			case "long" =>
			case "float" =>
			case "double" =>
			case "struct" =>
			case "class" =>
		}
	}
	
	for (i <- 0 to tokens.length) {
		val currNode = createNode(tokens(i))
	}
}