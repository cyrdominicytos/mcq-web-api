grammar QuestionAnswer;

file: (question (meta)* (option)+)* EOF;

question: '*' ' ' TEXT (NL TEXT)* NL;

meta: (':delay' | ':begindate' | ':complexity') ' ' TEXT NL;

option: ('+' | '-') ' ' TEXT (NL TEXT)* NL;

TEXT: ~[\r\n]+;
NL: '\r'? '\n';
WS: [ \t\r\n]+ -> skip;