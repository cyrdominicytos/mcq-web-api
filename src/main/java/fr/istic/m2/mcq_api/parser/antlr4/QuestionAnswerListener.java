package fr.istic.m2.mcq_api.parser.antlr4;// Generated from QuestionAnswer.g4 by ANTLR 4.13.0
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link QuestionAnswerParser}.
 */
public interface QuestionAnswerListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link QuestionAnswerParser#file}.
	 * @param ctx the parse tree
	 */
	void enterFile(QuestionAnswerParser.FileContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuestionAnswerParser#file}.
	 * @param ctx the parse tree
	 */
	void exitFile(QuestionAnswerParser.FileContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuestionAnswerParser#question}.
	 * @param ctx the parse tree
	 */
	void enterQuestion(QuestionAnswerParser.QuestionContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuestionAnswerParser#question}.
	 * @param ctx the parse tree
	 */
	void exitQuestion(QuestionAnswerParser.QuestionContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuestionAnswerParser#meta}.
	 * @param ctx the parse tree
	 */
	void enterMeta(QuestionAnswerParser.MetaContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuestionAnswerParser#meta}.
	 * @param ctx the parse tree
	 */
	void exitMeta(QuestionAnswerParser.MetaContext ctx);
	/**
	 * Enter a parse tree produced by {@link QuestionAnswerParser#option}.
	 * @param ctx the parse tree
	 */
	void enterOption(QuestionAnswerParser.OptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link QuestionAnswerParser#option}.
	 * @param ctx the parse tree
	 */
	void exitOption(QuestionAnswerParser.OptionContext ctx);
}