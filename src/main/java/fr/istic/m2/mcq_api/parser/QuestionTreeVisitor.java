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
public class QuestionTreeVisitor implements QuestionAnswerVisitor<List<ParsingQuestion>> {

    private List<ParsingQuestion> questions = new ArrayList<>();
    private ParsingQuestion currentQuestion;

    @Override
    public List<ParsingQuestion> visitFile(QuestionAnswerParser.FileContext ctx) {
        for (ParseTree child : ctx.children) {
            visit(child);
        }
        return questions;
    }

    @Override
    public List<ParsingQuestion> visitQuestion(QuestionAnswerParser.QuestionContext ctx) {
        return null;
    }
    public ParsingQuestion visitCQuestion(QuestionAnswerParser.QuestionContext ctx) {
        System.out.println("In visitCQuestion1 "+ctx);
        System.out.println("In visitCQuestion2 "+ctx.children.size());
        System.out.println("In visitCQuestion3 "+ctx.getStart());
        System.out.println("In visitCQuestion4 "+ctx.getSourceInterval());
        System.out.println("In visitCQuestion5 "+ctx.getText());

        currentQuestion = new ParsingQuestion();
        currentQuestion.setTitle(ctx.TEXT().toString());
        questions.add(currentQuestion);
        return currentQuestion;
    }

    @Override
    public List<ParsingQuestion> visitMeta(QuestionAnswerParser.MetaContext ctx) {
        return null;
    }

    public Meta visitCMeta(QuestionAnswerParser.MetaContext ctx) {
        Meta meta = null;
        System.out.println("In visitCMeta "+ctx);
        if (currentQuestion != null) {
            meta = new Meta();
            String[] parts = ctx.TEXT().getText().split(" ", 2);
            meta.setType(parts[0].substring(1)); // Remove the ':' character
            meta.setValue(parts.length > 1 ? parts[1] : "");
            currentQuestion.addMeta(meta);
        }

        /*if (currentQuestion != null) {
            meta = new Meta();
            meta.setType(ctx.META_KEY().getText());
            meta.setValue(ctx.TEXT().getText());
            currentQuestion.addMeta(meta);
        }*/
        return null;
    }

    @Override
    public List<ParsingQuestion> visitOption(QuestionAnswerParser.OptionContext ctx) {
        return null;
    }
    public Option visitCOption(QuestionAnswerParser.OptionContext ctx) {
        System.out.println("In visitCOption "+ctx);
        Option option = null;
        if (currentQuestion != null) {
             option = new Option();
            option.setType(ctx.getStart().getText());
            option.setText(ctx.TEXT().toString());
            currentQuestion.addOption(option);
        }
        return null;
    }

    @Override
    public List<ParsingQuestion> visit(ParseTree tree) {
        System.out.println("In visit, class: " + tree.getClass().getName() + ", text: " + tree.getText());
        if (tree instanceof QuestionAnswerParser.FileContext) {
            visitFile((QuestionAnswerParser.FileContext) tree);
        } else if (tree instanceof QuestionAnswerParser.QuestionContext) {
            return List.of(visitCQuestion((QuestionAnswerParser.QuestionContext) tree));
        } else if (tree instanceof QuestionAnswerParser.MetaContext) {
            visitCMeta((QuestionAnswerParser.MetaContext) tree);
        } else if (tree instanceof QuestionAnswerParser.OptionContext) {
            visitCOption((QuestionAnswerParser.OptionContext) tree);
        } else {
            for (int i = 0; i < tree.getChildCount(); i++) {
                visit(tree.getChild(i));
            }
        }
        return null;
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