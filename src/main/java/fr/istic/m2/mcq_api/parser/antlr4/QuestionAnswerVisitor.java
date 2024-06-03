package fr.istic.m2.mcq_api.parser.antlr4;// Generated from QuestionAnswer.g4 by ANTLR 4.13.0
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link QuestionAnswerParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface QuestionAnswerVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link QuestionAnswerParser#file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFile(QuestionAnswerParser.FileContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuestionAnswerParser#question}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuestion(QuestionAnswerParser.QuestionContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuestionAnswerParser#meta}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMeta(QuestionAnswerParser.MetaContext ctx);
	/**
	 * Visit a parse tree produced by {@link QuestionAnswerParser#option}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOption(QuestionAnswerParser.OptionContext ctx);
}