// Generated from Demo.g4 by ANTLR 4.7
package de.letsbuildacompiler.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DemoLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, IDENTIFIER=17, 
		NUMBER=18, WHITESPACE=19;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "IDENTIFIER", 
		"NUMBER", "WHITESPACE"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'if'", "'('", "')'", "'else'", "'{'", "'}'", "'/'", "'*'", 
		"'-'", "'+'", "'int'", "'='", "'println('", "'return'", "','"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, "IDENTIFIER", "NUMBER", "WHITESPACE"
	};
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


	public DemoLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Demo.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\25n\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3"+
		"\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\r\3"+
		"\r\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3"+
		"\20\3\20\3\20\3\20\3\20\3\21\3\21\3\22\3\22\7\22^\n\22\f\22\16\22a\13"+
		"\22\3\23\6\23d\n\23\r\23\16\23e\3\24\6\24i\n\24\r\24\16\24j\3\24\3\24"+
		"\2\2\25\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17"+
		"\35\20\37\21!\22#\23%\24\'\25\3\2\6\4\2C\\c|\5\2\62;C\\c|\3\2\62;\5\2"+
		"\13\f\17\17\"\"\2p\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13"+
		"\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2"+
		"\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2"+
		"!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\3)\3\2\2\2\5+\3\2\2\2\7.\3"+
		"\2\2\2\t\60\3\2\2\2\13\62\3\2\2\2\r\67\3\2\2\2\179\3\2\2\2\21;\3\2\2\2"+
		"\23=\3\2\2\2\25?\3\2\2\2\27A\3\2\2\2\31C\3\2\2\2\33G\3\2\2\2\35I\3\2\2"+
		"\2\37R\3\2\2\2!Y\3\2\2\2#[\3\2\2\2%c\3\2\2\2\'h\3\2\2\2)*\7=\2\2*\4\3"+
		"\2\2\2+,\7k\2\2,-\7h\2\2-\6\3\2\2\2./\7*\2\2/\b\3\2\2\2\60\61\7+\2\2\61"+
		"\n\3\2\2\2\62\63\7g\2\2\63\64\7n\2\2\64\65\7u\2\2\65\66\7g\2\2\66\f\3"+
		"\2\2\2\678\7}\2\28\16\3\2\2\29:\7\177\2\2:\20\3\2\2\2;<\7\61\2\2<\22\3"+
		"\2\2\2=>\7,\2\2>\24\3\2\2\2?@\7/\2\2@\26\3\2\2\2AB\7-\2\2B\30\3\2\2\2"+
		"CD\7k\2\2DE\7p\2\2EF\7v\2\2F\32\3\2\2\2GH\7?\2\2H\34\3\2\2\2IJ\7r\2\2"+
		"JK\7t\2\2KL\7k\2\2LM\7p\2\2MN\7v\2\2NO\7n\2\2OP\7p\2\2PQ\7*\2\2Q\36\3"+
		"\2\2\2RS\7t\2\2ST\7g\2\2TU\7v\2\2UV\7w\2\2VW\7t\2\2WX\7p\2\2X \3\2\2\2"+
		"YZ\7.\2\2Z\"\3\2\2\2[_\t\2\2\2\\^\t\3\2\2]\\\3\2\2\2^a\3\2\2\2_]\3\2\2"+
		"\2_`\3\2\2\2`$\3\2\2\2a_\3\2\2\2bd\t\4\2\2cb\3\2\2\2de\3\2\2\2ec\3\2\2"+
		"\2ef\3\2\2\2f&\3\2\2\2gi\t\5\2\2hg\3\2\2\2ij\3\2\2\2jh\3\2\2\2jk\3\2\2"+
		"\2kl\3\2\2\2lm\b\24\2\2m(\3\2\2\2\6\2_ej\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}