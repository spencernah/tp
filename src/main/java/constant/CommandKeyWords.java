package constant;

import exception.ErrorHandler;

public enum CommandKeyWords {
    SET_LOCATION("SET_LOCATION"), DELETE("DELETE"),
    APPOINTMENT("APPOINTMENT"), SET_TIME("SET_TIME"), CATEGORY("CATEGORY"), DONE("DONE"), VIEW("VIEW"), TODO("TODO"), BYE("BYE");
    private final String value;

    CommandKeyWords(String value) {
        this.value = value;
    }

    /**
     * Get serialized string.
     *
     * @return string which shows all available command keywords.
     */
    private static String getSerializedString() {
        String keywords = "";
        int numberOfKeys = values().length;
        int index = 0;
        for (CommandKeyWords v : values()) {
            index += 1;
            String punctuation = index == numberOfKeys ? "." : ", ";
            keywords = keywords.concat(v.getValue()).concat(punctuation);
        }

        return keywords;
    }

    /**
     * Command key words.
     *
     * @param value is an enum string for validating if the given value is valid.
     * @throws ErrorHandler customized error.
     */
    public static CommandKeyWords getEnum(String value) throws ErrorHandler {
        for (CommandKeyWords v : values()) {
            if (v.getValue().equalsIgnoreCase(value)) {
                return v;
            }
            else {
                CommandKeyWords w = autoCorrect(value, v);
                if (w != null) {
                    return w;
                }
            }
        }

        throw new ErrorHandler(ErrorMessage.INVALID_COMMAND + " Please starts your command with "
                + getSerializedString()
                + " (case insensitive)");

    }


    /**
     * Get value.
     *
     * @return enum value.
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    /**
     * Correct user's input Command in case of typo/misspelling.
     *
     * @param input is the user's input
     * @param v is an enum
     */
    private static CommandKeyWords autoCorrect(String input, CommandKeyWords v) {
        double similarity;
        int iter;
        double comparison;
        boolean isSkip;

        String comp = v.getValue();
        similarity = 0;
        iter = 0;
        for (int i = 0; i < comp.length(); i++) {
            isSkip = false;
            for (int j = iter; j < input.length() && !isSkip; j++) {
                if (input.charAt(j) ==  comp.charAt(i)) {
                    similarity++;
                    iter++;
                    isSkip = true;
                }
                comparison = similarity/comp.length();

                System.out.println(v.getValue() + "/" + input.charAt(j) + "/" + comp.charAt(i));

                if (comparison>0.5) {
                    return v;
                }
            }
        }
        return null;
    }
}

