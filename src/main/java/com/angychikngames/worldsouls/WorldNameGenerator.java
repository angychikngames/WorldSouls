package com.angychikngames.worldsouls;

import java.util.Random;

public final class WorldNameGenerator {
    private static final String[] STARTS = {
        "a", "e", "i", "o", "u", "al", "ar", "el", "il", "or", "ur", "av", "an",
        "en", "ir", "is", "az", "ath", "ner", "mor", "tal", "vel", "ser", "dal",
        "sol", "ter", "cal", "mar", "lor", "var", "tor", "num", "ara", "ela",
        "ava", "ori", "eru", "ivo", "ona", "ily", "sela", "nora", "ael", "dara", "sora"
    };
    private static final String[] MIDDLES = {
        "la", "le", "li", "ra", "re", "ri", "ro", "ma", "me", "mi",
        "na", "ne", "ni", "no", "va", "ve", "sa", "se", "ta", "te", "da", "de"
    };
    private static final String[] ENDINGS = {
        "a", "e", "i", "o", "u", "an", "en", "in", "on", "un", "al", "el", "il",
        "ol", "ar", "er", "ir", "or", "is", "as", "os", "eth", "oth", "us", "urn",
        "ath", "rel", "ren", "riel", "ran", "rin", "ron", "lan", "len", "lin",
        "lon", "lar", "lor", "nal", "nel", "nor", "val", "ven", "van", "mar",
        "mir", "mor", "men", "mun", "mea", "ra", "ru", "la", "li", "na", "nu",
        "va", "via", "ria", "lia", "ena", "ela", "ora", "ara", "ira", "ila"
    };
    private static final String[] REFERENCE_STARTS = {
        "tatoo", "nab", "alder", "corus", "dan", "korri", "ty",
        "il", "musta", "kash", "datho", "manda", "mala", "mor",
        "onde", "ry", "bes", "dago", "kami", "geo", "sca",
        "lo", "jed", "ho", "en", "core", "os", "felu",
        "uta", "sere", "ro", "mon", "vul", "rom", "re",
        "qo", "an", "ba", "beta", "ri", "feren", "card",
        "tri", "deno", "tel", "ta", "pro", "arra", "cala",
        "gie", "kai", "salu", "ze", "tal", "ae", "phaa",
        "rea", "sang", "doi", "jan", "thes", "pala", "tu",
        "sur", "ran", "fe", "no", "vir", "eden", "i",
        "aze", "drae", "ar", "ka", "mar", "xo", "nath",
        "elu", "ni", "mas", "se", "obli", "hydae", "zo",
        "sp", "vali", "vana", "eo", "ne", "eu", "io",
        "fun", "sava", "terra", "fen", "cad", "mac", "noc",
        "sol", "mag", "betel", "tra", "hel", "hyper", "cap",
        "ko", "gal", "sk", "ave", "ast", "pan", "ath",
        "art", "apo", "he", "perse", "de", "her", "posei",
        "dio", "hepha", "gai", "ny", "e", "oura", "chro",
        "rh", "the", "oce", "teth", "ja", "ves", "ju",
        "ce", "min", "nep", "sa", "ve", "mer", "plu",
        "o", "fri", "fre", "frey", "tho", "id", "ska",
        "njo", "bal", "ei", "si", "anu", "hat", "sekh",
        "bas", "a", "neph", "in", "ish", "nin", "eresh",
        "tia", "ama", "tsuku", "susa", "iza", "quetzal", "huitzilo",
        "neme", "har", "pho", "dei", "hes", "hyp", "thana",
        "aphro", "dia", "bello", "for", "au", "po", "flo",
        "fau", "sil", "quiri", "ter", "bri", "cern", "rhi",
        "cerr", "arian", "da", "lu", "man", "nua", "og",
        "morri", "aen", "bele", "tara", "su", "per", "mo",
        "sva", "dazh", "stri", "la", "mora", "zor", "uk",
        "ilma", "mie", "ah", "nyy", "lai", "sau", "me",
        "ga", "aus",
        "aby", "chul", "daka", "toll", "ori", "cel", "atlan",
        "helio", "kh", "pracla", "tao", "visu", "lan", "sat",
        "ge", "asu", "otha", "ere", "icar", "des", "so",
        "nir", "olo", "camu", "qe", "kal", "te", "na",
        "tag", "han"
    };
    private static final String[] REFERENCE_SYLLABLES = {
        "ine", "oo", "aan", "cant", "tooine", "ban", "thon",
        "um", "far", "yyyk", "mir", "lore", "chor", "tis",
        "ron", "loth", "pin", "bah", "no", "nosis", "rif",
        "thal", "ha", "th", "dor", "llia", "sus", "cia",
        "pau", "nno", "dia", "cala", "can", "ulus", "mus",
        "nos", "doria", "jor", "zed", "sa", "ginar", "assia",
        "ll", "bula", "lar", "los", "gel", "cyon", "kis",
        "dan", "di", "tain", "chese", "bes", "lon", "ther",
        "ze", "ch", "helios", "laho", "sac", "jur", "sia",
        "ven", "chanka", "kesh", "noch", "lium", "ros", "veria",
        "mire", "prime", "roth", "nor", "gus", "resh", "dum",
        "reza", "ne", "rn", "ser", "cunda", "therius", "vion",
        "lyn", "diark", "ira", "sthea", "diel", "valice", "rzea",
        "ssus", "ropa", "na", "dament", "thun", "nova", "ris",
        "ia", "ragge", "turne", "aris", "rathea", "geuse", "ntor",
        "ion", "rica", "bol", "lifrey", "aro", "tera", "lorn",
        "raea", "dora", "ena", "emis", "llo", "cate", "phone",
        "meter", "ra", "mes", "don", "nysus", "estus", "a",
        "x", "lene", "os", "lios", "eria", "ea",
        "ys", "nus", "ta", "res", "erva", "piter", "tune",
        "turn", "cury", "to", "din", "gg", "ya", "r",
        "ki", "unn", "rd", "dr", "f", "sis", "siris",
        "bis", "hor", "met", "tet", "mun", "rus", "thys",
        "anna", "tar", "lil", "hursag", "kigal", "mat", "duk",
        "terasu", "yomi", "noo", "nagi", "nami", "coatl", "pochtli",
        "ke", "monia", "che", "bos", "mos", "tia", "be",
        "tos", "rebos", "dite", "tuna", "rora", "mona", "vanus",
        "minus", "gid", "unnos", "annon", "idwen", "rhod", "nu",
        "gh", "annan", "da", "ma", "gan", "pona", "nis",
        "cellus", "un", "les", "kosh", "rog", "bog", "glav",
        "ko", "pio", "likki", "ti", "rikki", "kunas", "le",
        "ness", "myna", "bija", "teja",
        "dos", "ak", "ana", "lla", "estis", "polis", "eb",
        "rush", "nas", "tea", "eda", "ff", "nii", "ras",
        "la", "bus", "us", "tiny", "phis", "al", "kar",
        "rti", "kun", "lus", "tesh", "tu", "lana", "galus",
        "gara", "ronda", "gar", "sya", "rea", "ka"
    };
    private static final String[] DESIGNATIONS = {
        "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII",
        "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX"
    };

    private WorldNameGenerator() {
    }

    public static String generate(Random random) {
        boolean referenceRoll = random.nextInt(100) == 0;
        int vowelLimit = referenceRoll || random.nextInt(8) == 0 ? 2 : 1;
        // borrow one reference piece on about half the regular rolls
        int referencePiece = referenceRoll ? 0 : random.nextInt(4);
        String[] starts = referenceRoll || referencePiece == 0 ? REFERENCE_STARTS : STARTS;
        String[] endings = referenceRoll || referencePiece == 1 ? REFERENCE_SYLLABLES : ENDINGS;
        String name;
        do {
            String start = starts[random.nextInt(starts.length)];
            String middle = !referenceRoll && random.nextInt(4) == 0
                    ? MIDDLES[random.nextInt(MIDDLES.length)] : "";
            String ending = endings[random.nextInt(endings.length)];

            boolean apostrophe = !referenceRoll && random.nextInt(20) == 0 && start.length() > 1;
            if (apostrophe) {
                start += "'";
            }
            name = join(join(start, middle), ending);
        } while (name.length() < 3 || name.length() > 16
                || busySounds(name, vowelLimit) || NameFilter.isBlocked(name));
        name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
        return addDesignation(name, random);
    }

    private static String join(String left, String right) {
        if (right.isEmpty()) {
            return left;
        }
        char last = left.charAt(left.length() - 1);
        char first = right.charAt(0);
        // give a closed syllable a short vowel before another consonant
        if (last != '\'' && "aeiouy".indexOf(last) < 0
                && "aeiouy".indexOf(first) < 0 && "lrnm".indexOf(last) < 0) {
            return left + "a" + right;
        }
        return left + right;
    }

    private static String addDesignation(String name, Random random) {
        if (random.nextInt(20) == 0) {
            int index = random.nextInt(10) == 0
                    ? 12 + random.nextInt(8)
                    : random.nextInt(12);
            String designation = DESIGNATIONS[index];
            if (name.length() + 1 + designation.length() <= 16) {
                return name + " " + designation;
            }
        }
        return name;
    }
    private static boolean busySounds(String name, int vowelLimit) {
        if (name.matches(".*(?:th|kh|zh|sh|ch|ph|qu|[jkqxz])'?(?:th|kh|zh|sh|ch|ph|qu|[jkqxz]).*")
                || name.matches(".*(th|kh|zh|sh|ch|ph)[aeiouy]+\1.*")) {
            return true;
        }

        String sounds = name.replace("qu", "q").replaceAll("(th|kh|zh|sh|ch|ph)", "h");
        if (sounds.matches(".*[^aeiouy']{3}.*")) {
            return true;
        }
        int pairs = 0;
        for (int i = 1; i < sounds.length(); i++) {
            if ("aeiou".indexOf(sounds.charAt(i - 1)) >= 0 && "aeiou".indexOf(sounds.charAt(i)) >= 0) {
                // once is enough for the same vowel pair
                if (sounds.indexOf(sounds.substring(i - 1, i + 1), i) >= 0) {
                    return true;
                }
                pairs++;
            }
        }
        return pairs > vowelLimit;
    }
}


