package fr.istic.m2.mcq_api.parser;


import fr.istic.m2.mcq_api.parser.antlr4.QuestionAnswerBaseVisitor;
import fr.istic.m2.mcq_api.parser.antlr4.QuestionAnswerParser;
import fr.istic.m2.mcq_api.parser.antlr4.QuestionAnswerVisitor;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class QuestionAnswerConcreteVisitor extends QuestionAnswerBaseVisitor<Object> {
    @Override
    public List<ParsingQuestion> visitFile(QuestionAnswerParser.FileContext ctx) {
        List<ParsingQuestion> questions = new ArrayList<>();

        System.out.println("question().size "+ctx.question().size());
        for (QuestionAnswerParser.QuestionContext questionCtx : ctx.question()) {
            System.out.println("In loop "+ctx.getText());
            ParsingQuestion question = visitQuestion(questionCtx);
            questions.add(question);
        }
        System.out.println("file "+ctx.getText());
        return questions;
    }

    @Override
    public ParsingQuestion visitQuestion(QuestionAnswerParser.QuestionContext ctx) {
        ParsingQuestion question = new ParsingQuestion();
        question.setTitle(ctx.TEXT().toString());
       /* for (QuestionAnswerParser.MetaContext metaCtx : ctx.) {
            Meta metaData = visitMeta(metaCtx);
            question.addMeta(metaData);
        }
        for (QuestionAnswerParser.OptionContext optionCtx : ctx.op()) {
            Option option = visitOption(optionCtx);
            question.addOption(option);
        }*/
        return question;
    }

    @Override
    public Meta visitMeta(QuestionAnswerParser.MetaContext ctx) {
        Meta metaData = new Meta();
        metaData.setType(ctx.getStart().getText());
        metaData.setValue(ctx.TEXT().getText());
        return metaData;
    }

    @Override
    public Option visitOption(QuestionAnswerParser.OptionContext ctx) {
        Option option = new Option();
        option.setType(ctx.getStart().getText());
        option.setText(ctx.TEXT().toString());
        return option;
    }

    @Override
    public List<ParsingQuestion> visit(ParseTree tree) {
        return visitFile((QuestionAnswerParser.FileContext) tree);
    }

    @Override
    public List<ParsingQuestion> visitChildren(RuleNode node) {
        return null;
    }

    @Override
    public List<ParsingQuestion> visitTerminal(TerminalNode node) {
        return null;
    }

    @Override
    public List<ParsingQuestion> visitErrorNode(ErrorNode node) {
        return null;
    }
}
