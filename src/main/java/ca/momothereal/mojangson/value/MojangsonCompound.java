package ca.momothereal.mojangson.value;

import ca.momothereal.mojangson.MojangsonFinder;
import ca.momothereal.mojangson.ex.MojangsonParseException;

import java.util.HashMap;
import java.util.Map;

import static ca.momothereal.mojangson.MojangsonToken.*;

public class MojangsonCompound extends HashMap<String, MojangsonValue> implements MojangsonValue<Map<String, MojangsonValue>> {

    private final int C_COMPOUND_START = 0;      // Parsing context
    private final int C_COMPOUND_PAIR_KEY = 1;   // Parsing context
    private final int C_COMPOUND_PAIR_VALUE = 2; // Parsing context

    public MojangsonCompound() {

    }

    public MojangsonCompound(Map map) {
        super(map);
    }

    @Override
    public void write(StringBuilder builder) {
        builder.append(COMPOUND_START);
        boolean start = true;

        for (String key : keySet()) {
            if (start) {
                start = false;
            } else {
                builder.append(ELEMENT_SEPERATOR);
            }

            builder.append(key).append(ELEMENT_PAIR_SEPERATOR);
            MojangsonValue value = get(key);
            value.write(builder);
        }
        builder.append(COMPOUND_END);
    }

    @Override
    public void read(String string) throws MojangsonParseException {
        int context = C_COMPOUND_START;
        String tmp_key = "", tmp_val = "";
        int scope = 0;

        for (int index = 0; index < string.length(); index++) {
            Character character = string.charAt(index);
            if (character == COMPOUND_START.getSymbol() || character == ARRAY_START.getSymbol()) {
                scope++;
            }
            if (character == COMPOUND_END.getSymbol() || character == ARRAY_END.getSymbol()) {
                scope--;
            }
            if (context == C_COMPOUND_START) {
                if (character != COMPOUND_START.getSymbol()) {
                    parseException(index, character);
                    return;
                }
                context++;
                continue;
            }
            if (context == C_COMPOUND_PAIR_KEY) {
                if (character == ELEMENT_PAIR_SEPERATOR.getSymbol() && scope <= 1) {
                    context++;
                    continue;
                }
                tmp_key += character;
                continue;
            }
            if (context == C_COMPOUND_PAIR_VALUE) {
                if ((character == ELEMENT_SEPERATOR.getSymbol() || character == COMPOUND_END.getSymbol()) && scope <= 1) {
                    context = C_COMPOUND_PAIR_KEY;
                    put(tmp_key, MojangsonFinder.readFromValue(tmp_val));
                    tmp_key = tmp_val = "";
                    continue;
                }
                tmp_val += character;
            }
        }
    }

    @Override
    public Map<String, MojangsonValue> getValue() {
        return this;
    }

    private void parseException(int index, char symbol) throws MojangsonParseException {
        throw new MojangsonParseException("Index: " + index + ", symbol: \'" + symbol + "\'", MojangsonParseException.ParseExceptionReason.UNEXPECTED_SYMBOL);
    }
}
