package fr.istic.m2.mcq_api.parser.antlr4;// Generated from QuestionAnswer.g4 by ANTLR 4.13.0

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class QuestionAnswerLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, TEXT=8, NL=9, 
		WS=10;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "TEXT", "NL", 
			"WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'*'", "' '", "':delay'", "':begindate'", "':complexity'", "'+'", 
			"'-'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, "TEXT", "NL", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public QuestionAnswerLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "QuestionAnswer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\nL\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0001\u0000\u0001\u0000\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001"+
		"\u0007\u0004\u0007=\b\u0007\u000b\u0007\f\u0007>\u0001\b\u0003\bB\b\b"+
		"\u0001\b\u0001\b\u0001\t\u0004\tG\b\t\u000b\t\f\tH\u0001\t\u0001\t\u0000"+
		"\u0000\n\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b"+
		"\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0001\u0000\u0002\u0002\u0000\n"+
		"\n\r\r\u0003\u0000\t\n\r\r  N\u0000\u0001\u0001\u0000\u0000\u0000\u0000"+
		"\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000"+
		"\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b"+
		"\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001"+
		"\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001"+
		"\u0000\u0000\u0000\u0001\u0015\u0001\u0000\u0000\u0000\u0003\u0017\u0001"+
		"\u0000\u0000\u0000\u0005\u0019\u0001\u0000\u0000\u0000\u0007 \u0001\u0000"+
		"\u0000\u0000\t+\u0001\u0000\u0000\u0000\u000b7\u0001\u0000\u0000\u0000"+
		"\r9\u0001\u0000\u0000\u0000\u000f<\u0001\u0000\u0000\u0000\u0011A\u0001"+
		"\u0000\u0000\u0000\u0013F\u0001\u0000\u0000\u0000\u0015\u0016\u0005*\u0000"+
		"\u0000\u0016\u0002\u0001\u0000\u0000\u0000\u0017\u0018\u0005 \u0000\u0000"+
		"\u0018\u0004\u0001\u0000\u0000\u0000\u0019\u001a\u0005:\u0000\u0000\u001a"+
		"\u001b\u0005d\u0000\u0000\u001b\u001c\u0005e\u0000\u0000\u001c\u001d\u0005"+
		"l\u0000\u0000\u001d\u001e\u0005a\u0000\u0000\u001e\u001f\u0005y\u0000"+
		"\u0000\u001f\u0006\u0001\u0000\u0000\u0000 !\u0005:\u0000\u0000!\"\u0005"+
		"b\u0000\u0000\"#\u0005e\u0000\u0000#$\u0005g\u0000\u0000$%\u0005i\u0000"+
		"\u0000%&\u0005n\u0000\u0000&\'\u0005d\u0000\u0000\'(\u0005a\u0000\u0000"+
		"()\u0005t\u0000\u0000)*\u0005e\u0000\u0000*\b\u0001\u0000\u0000\u0000"+
		"+,\u0005:\u0000\u0000,-\u0005c\u0000\u0000-.\u0005o\u0000\u0000./\u0005"+
		"m\u0000\u0000/0\u0005p\u0000\u000001\u0005l\u0000\u000012\u0005e\u0000"+
		"\u000023\u0005x\u0000\u000034\u0005i\u0000\u000045\u0005t\u0000\u0000"+
		"56\u0005y\u0000\u00006\n\u0001\u0000\u0000\u000078\u0005+\u0000\u0000"+
		"8\f\u0001\u0000\u0000\u00009:\u0005-\u0000\u0000:\u000e\u0001\u0000\u0000"+
		"\u0000;=\b\u0000\u0000\u0000<;\u0001\u0000\u0000\u0000=>\u0001\u0000\u0000"+
		"\u0000><\u0001\u0000\u0000\u0000>?\u0001\u0000\u0000\u0000?\u0010\u0001"+
		"\u0000\u0000\u0000@B\u0005\r\u0000\u0000A@\u0001\u0000\u0000\u0000AB\u0001"+
		"\u0000\u0000\u0000BC\u0001\u0000\u0000\u0000CD\u0005\n\u0000\u0000D\u0012"+
		"\u0001\u0000\u0000\u0000EG\u0007\u0001\u0000\u0000FE\u0001\u0000\u0000"+
		"\u0000GH\u0001\u0000\u0000\u0000HF\u0001\u0000\u0000\u0000HI\u0001\u0000"+
		"\u0000\u0000IJ\u0001\u0000\u0000\u0000JK\u0006\t\u0000\u0000K\u0014\u0001"+
		"\u0000\u0000\u0000\u0004\u0000>AH\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}