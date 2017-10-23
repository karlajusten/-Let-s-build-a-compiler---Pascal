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
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, IDENTIFIER=21, NUMBER=22, WHITESPACE=23;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "IDENTIFIER", "NUMBER", "WHITESPACE"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'if'", "'('", "')'", "'else'", "'{'", "'}'", "'/'", "'*'", 
		"'-'", "'+'", "'<'", "'<='", "'>'", "'>='", "'int'", "'='", "'println('", 
		"'return'", "','"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, "IDENTIFIER", "NUMBER", 
		"WHITESPACE"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\31\u0080\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2"+
		"\3\2\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3"+
		"\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\20"+
		"\3\20\3\20\3\21\3\21\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\26\3\26"+
		"\7\26p\n\26\f\26\16\26s\13\26\3\27\6\27v\n\27\r\27\16\27w\3\30\6\30{\n"+
		"\30\r\30\16\30|\3\30\3\30\2\2\31\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31"+
		"\3\2\6\4\2C\\c|\5\2\62;C\\c|\3\2\62;\5\2\13\f\17\17\"\"\2\u0082\2\3\3"+
		"\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2"+
		"\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3"+
		"\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2"+
		"%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\3\61"+
		"\3\2\2\2\5\63\3\2\2\2\7\66\3\2\2\2\t8\3\2\2\2\13:\3\2\2\2\r?\3\2\2\2\17"+
		"A\3\2\2\2\21C\3\2\2\2\23E\3\2\2\2\25G\3\2\2\2\27I\3\2\2\2\31K\3\2\2\2"+
		"\33M\3\2\2\2\35P\3\2\2\2\37R\3\2\2\2!U\3\2\2\2#Y\3\2\2\2%[\3\2\2\2\'d"+
		"\3\2\2\2)k\3\2\2\2+m\3\2\2\2-u\3\2\2\2/z\3\2\2\2\61\62\7=\2\2\62\4\3\2"+
		"\2\2\63\64\7k\2\2\64\65\7h\2\2\65\6\3\2\2\2\66\67\7*\2\2\67\b\3\2\2\2"+
		"89\7+\2\29\n\3\2\2\2:;\7g\2\2;<\7n\2\2<=\7u\2\2=>\7g\2\2>\f\3\2\2\2?@"+
		"\7}\2\2@\16\3\2\2\2AB\7\177\2\2B\20\3\2\2\2CD\7\61\2\2D\22\3\2\2\2EF\7"+
		",\2\2F\24\3\2\2\2GH\7/\2\2H\26\3\2\2\2IJ\7-\2\2J\30\3\2\2\2KL\7>\2\2L"+
		"\32\3\2\2\2MN\7>\2\2NO\7?\2\2O\34\3\2\2\2PQ\7@\2\2Q\36\3\2\2\2RS\7@\2"+
		"\2ST\7?\2\2T \3\2\2\2UV\7k\2\2VW\7p\2\2WX\7v\2\2X\"\3\2\2\2YZ\7?\2\2Z"+
		"$\3\2\2\2[\\\7r\2\2\\]\7t\2\2]^\7k\2\2^_\7p\2\2_`\7v\2\2`a\7n\2\2ab\7"+
		"p\2\2bc\7*\2\2c&\3\2\2\2de\7t\2\2ef\7g\2\2fg\7v\2\2gh\7w\2\2hi\7t\2\2"+
		"ij\7p\2\2j(\3\2\2\2kl\7.\2\2l*\3\2\2\2mq\t\2\2\2np\t\3\2\2on\3\2\2\2p"+
		"s\3\2\2\2qo\3\2\2\2qr\3\2\2\2r,\3\2\2\2sq\3\2\2\2tv\t\4\2\2ut\3\2\2\2"+
		"vw\3\2\2\2wu\3\2\2\2wx\3\2\2\2x.\3\2\2\2y{\t\5\2\2zy\3\2\2\2{|\3\2\2\2"+
		"|z\3\2\2\2|}\3\2\2\2}~\3\2\2\2~\177\b\30\2\2\177\60\3\2\2\2\6\2qw|\3\b"+
		"\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}