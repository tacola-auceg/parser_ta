package analyser;
interface Taggs
{
		TabMethods tm = new TabMethods();

		byte[] TagNoun = tm.convert(" < Noun > ");
		byte[] TagVerb = tm.convert(" < Verb > ");
		byte[] TagRelativeParticipleSuffix = tm.convert(" < Relative Participle Suffix> ");
		byte[] TagPlural = tm.convert(" < plural > ");
		byte[] TagOblique =tm.convert(" < Oblique > ");
//		byte[] TagCase = tm.convert(" < case >");
		byte[] TagAccCase = tm.convert(" < Accusative case >");
		byte[] TagInsCase = tm.convert(" < Instrumental case >");
		byte[] TagDatCase = tm.convert(" < Dative case >");
		byte[] TagBenCase = tm.convert(" < Benefactive case >");
		byte[] TagSocCase = tm.convert(" < Sociative case >");
		byte[] TagLocCase = tm.convert(" < Locative case >");
		byte[] TagAblCase = tm.convert(" < Ablative case >");
		byte[] TagGenCase = tm.convert(" < Genetive case >");

		byte[] TagAdverbialSuffix = tm.convert(" < Adverbial Suffix > ");
		byte[] TagAdjectivalSuffix = tm.convert(" < Adjectival Suffix > ");
		byte[] TagVerbalParticiple = tm.convert(" < verbal participle > ");
		byte[] TagPastTenseMarker = tm.convert(" < past tense marker > ");
		byte[] TagPresentTenseMarker = tm.convert(" < present tense marker > ");
		byte[] TagFutureTenseMarker = tm.convert(" < future tense marker > ");
		byte[] TagDoublingConsonant = tm.convert(" < Doubling Consonent > ");
		byte[] TagClitic = tm.convert(" < clitic > ");
		byte[] TagCliticum = tm.convert(" < clitic / Neuter Gender > ");
		byte[] TagConditionalSuffix = tm.convert(" < conditionalsuffix > ");
		byte[] TagInfinitive = tm.convert(" < infinitive > ");
		byte[] TagThal = tm.convert(" < Thal > ");
		byte[] TagPosPos = tm.convert(" < PostPosition > ");
		byte[] TagPotentialSuffix = tm.convert(" < Potential Suffix > ");

}
