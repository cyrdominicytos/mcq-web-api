package fr.istic.m2.mcq_api.parser.antlr4;// Generated from QuestionAnswer.g4 by ANTLR 4.13.0

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class QuestionAnswerParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, TEXT=8, NL=9, 
		WS=10;
	public static final int
		RULE_file = 0, RULE_question = 1, RULE_meta = 2, RULE_option = 3;
	private static String[] makeRuleNames() {
		return new String[] {
			"file", "question", "meta", "option"
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

	@Override
	public String getGrammarFileName() { return "QuestionAnswer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public QuestionAnswerParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FileContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(QuestionAnswerParser.EOF, 0); }
		public List<QuestionContext> question() {
			return getRuleContexts(QuestionContext.class);
		}
		public QuestionContext question(int i) {
			return getRuleContext(QuestionContext.class,i);
		}
		public List<MetaContext> meta() {
			return getRuleContexts(MetaContext.class);
		}
		public MetaContext meta(int i) {
			return getRuleContext(MetaContext.class,i);
		}
		public List<OptionContext> option() {
			return getRuleContexts(OptionContext.class);
		}
		public OptionContext option(int i) {
			return getRuleContext(OptionContext.class,i);
		}
		public FileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_file; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuestionAnswerListener) ((QuestionAnswerListener)listener).enterFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuestionAnswerListener) ((QuestionAnswerListener)listener).exitFile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuestionAnswerVisitor) return ((QuestionAnswerVisitor<? extends T>)visitor).visitFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FileContext file() throws RecognitionException {
		FileContext _localctx = new FileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_file);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(22);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(8);
				question();
				setState(12);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 56L) != 0)) {
					{
					{
					setState(9);
					meta();
					}
					}
					setState(14);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(16); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(15);
					option();
					}
					}
					setState(18); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__5 || _la==T__6 );
				}
				}
				setState(24);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(25);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QuestionContext extends ParserRuleContext {
		public List<TerminalNode> TEXT() { return getTokens(QuestionAnswerParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(QuestionAnswerParser.TEXT, i);
		}
		public List<TerminalNode> NL() { return getTokens(QuestionAnswerParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(QuestionAnswerParser.NL, i);
		}
		public QuestionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_question; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuestionAnswerListener) ((QuestionAnswerListener)listener).enterQuestion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuestionAnswerListener) ((QuestionAnswerListener)listener).exitQuestion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuestionAnswerVisitor) return ((QuestionAnswerVisitor<? extends T>)visitor).visitQuestion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuestionContext question() throws RecognitionException {
		QuestionContext _localctx = new QuestionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_question);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			match(T__0);
			setState(28);
			match(T__1);
			setState(29);
			match(TEXT);
			setState(34);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(30);
					match(NL);
					setState(31);
					match(TEXT);
					}
					} 
				}
				setState(36);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			setState(37);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MetaContext extends ParserRuleContext {
		public TerminalNode TEXT() { return getToken(QuestionAnswerParser.TEXT, 0); }
		public TerminalNode NL() { return getToken(QuestionAnswerParser.NL, 0); }
		public MetaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_meta; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuestionAnswerListener) ((QuestionAnswerListener)listener).enterMeta(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuestionAnswerListener) ((QuestionAnswerListener)listener).exitMeta(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuestionAnswerVisitor) return ((QuestionAnswerVisitor<? extends T>)visitor).visitMeta(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MetaContext meta() throws RecognitionException {
		MetaContext _localctx = new MetaContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_meta);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 56L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(40);
			match(T__1);
			setState(41);
			match(TEXT);
			setState(42);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OptionContext extends ParserRuleContext {
		public List<TerminalNode> TEXT() { return getTokens(QuestionAnswerParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(QuestionAnswerParser.TEXT, i);
		}
		public List<TerminalNode> NL() { return getTokens(QuestionAnswerParser.NL); }
		public TerminalNode NL(int i) {
			return getToken(QuestionAnswerParser.NL, i);
		}
		public OptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_option; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QuestionAnswerListener) ((QuestionAnswerListener)listener).enterOption(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QuestionAnswerListener) ((QuestionAnswerListener)listener).exitOption(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof QuestionAnswerVisitor) return ((QuestionAnswerVisitor<? extends T>)visitor).visitOption(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OptionContext option() throws RecognitionException {
		OptionContext _localctx = new OptionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_option);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			_la = _input.LA(1);
			if ( !(_la==T__5 || _la==T__6) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(45);
			match(T__1);
			setState(46);
			match(TEXT);
			setState(51);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			while ( _alt!=2 && _alt!= ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(47);
					match(NL);
					setState(48);
					match(TEXT);
					}
					} 
				}
				setState(53);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
			}
			setState(54);
			match(NL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\n9\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0001\u0000\u0001\u0000\u0005"+
		"\u0000\u000b\b\u0000\n\u0000\f\u0000\u000e\t\u0000\u0001\u0000\u0004\u0000"+
		"\u0011\b\u0000\u000b\u0000\f\u0000\u0012\u0005\u0000\u0015\b\u0000\n\u0000"+
		"\f\u0000\u0018\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001!\b\u0001\n\u0001\f\u0001"+
		"$\t\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0005\u00032\b\u0003\n\u0003\f\u00035\t\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0000\u0000\u0004\u0000\u0002\u0004\u0006\u0000"+
		"\u0002\u0001\u0000\u0003\u0005\u0001\u0000\u0006\u00079\u0000\u0016\u0001"+
		"\u0000\u0000\u0000\u0002\u001b\u0001\u0000\u0000\u0000\u0004\'\u0001\u0000"+
		"\u0000\u0000\u0006,\u0001\u0000\u0000\u0000\b\f\u0003\u0002\u0001\u0000"+
		"\t\u000b\u0003\u0004\u0002\u0000\n\t\u0001\u0000\u0000\u0000\u000b\u000e"+
		"\u0001\u0000\u0000\u0000\f\n\u0001\u0000\u0000\u0000\f\r\u0001\u0000\u0000"+
		"\u0000\r\u0010\u0001\u0000\u0000\u0000\u000e\f\u0001\u0000\u0000\u0000"+
		"\u000f\u0011\u0003\u0006\u0003\u0000\u0010\u000f\u0001\u0000\u0000\u0000"+
		"\u0011\u0012\u0001\u0000\u0000\u0000\u0012\u0010\u0001\u0000\u0000\u0000"+
		"\u0012\u0013\u0001\u0000\u0000\u0000\u0013\u0015\u0001\u0000\u0000\u0000"+
		"\u0014\b\u0001\u0000\u0000\u0000\u0015\u0018\u0001\u0000\u0000\u0000\u0016"+
		"\u0014\u0001\u0000\u0000\u0000\u0016\u0017\u0001\u0000\u0000\u0000\u0017"+
		"\u0019\u0001\u0000\u0000\u0000\u0018\u0016\u0001\u0000\u0000\u0000\u0019"+
		"\u001a\u0005\u0000\u0000\u0001\u001a\u0001\u0001\u0000\u0000\u0000\u001b"+
		"\u001c\u0005\u0001\u0000\u0000\u001c\u001d\u0005\u0002\u0000\u0000\u001d"+
		"\"\u0005\b\u0000\u0000\u001e\u001f\u0005\t\u0000\u0000\u001f!\u0005\b"+
		"\u0000\u0000 \u001e\u0001\u0000\u0000\u0000!$\u0001\u0000\u0000\u0000"+
		"\" \u0001\u0000\u0000\u0000\"#\u0001\u0000\u0000\u0000#%\u0001\u0000\u0000"+
		"\u0000$\"\u0001\u0000\u0000\u0000%&\u0005\t\u0000\u0000&\u0003\u0001\u0000"+
		"\u0000\u0000\'(\u0007\u0000\u0000\u0000()\u0005\u0002\u0000\u0000)*\u0005"+
		"\b\u0000\u0000*+\u0005\t\u0000\u0000+\u0005\u0001\u0000\u0000\u0000,-"+
		"\u0007\u0001\u0000\u0000-.\u0005\u0002\u0000\u0000.3\u0005\b\u0000\u0000"+
		"/0\u0005\t\u0000\u000002\u0005\b\u0000\u00001/\u0001\u0000\u0000\u0000"+
		"25\u0001\u0000\u0000\u000031\u0001\u0000\u0000\u000034\u0001\u0000\u0000"+
		"\u000046\u0001\u0000\u0000\u000053\u0001\u0000\u0000\u000067\u0005\t\u0000"+
		"\u00007\u0007\u0001\u0000\u0000\u0000\u0005\f\u0012\u0016\"3";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}