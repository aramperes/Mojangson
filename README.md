

What is *Mojangson*?
-------
*Mojangson* is a variant of JSON, used in the voxel-based game Minecraft by Mojang. It's current use is to get a readable representation of [Data tags](http://minecraft.gamepedia.com/Commands#Data_tags), which are used to read and modify data from technical entities of the game. 

*Mojangson* is slightly different than JSON because of it's relative flexibility. For example, certain rules such as *double-quotes* for element key names are not mandatory. However, the data representation can be somewhat variable, numerical values may need an extra prefix with their literal value, which makes the use of standart JSON parsers impossible.

*Mojangson* can represent different literal values: Byte, Double, Float, Integer, Long, Short and String. The syntax also supports the use of *Compounds*, which is a nested Map with a standart key-value pair syntax, and the use of *Arrays*, which is a List of values (without a key) of the same type.

API Example
-------
This is an example of a simple Mojangson string:

    {Name:"Momo",Age:100s,Height:1.89d}

This string can be broken down like this:

    { // Compound start
         Name:"Momo", // MojangsonString value, key is 'Name'
         Age:100s     // MojangsonShort value ('s' suffix), key is 'Age'
         Height:1.89d // MojangsonDouble value ('d' suffix), key is 'Height'
    } // Compound end

In order to represent the parent compound, we need to invoke the `MojangsonCompound#read(String)` method:

```java
	String mojangson = "{Name:\"Momo\",Age:100s,Height:1.89d}";
	MojangsonCompound compound = new MojangsonCompound();
	try {
        compound.read(string);
    } catch (MojangsonParseException e) {
        e.printStackTrace();
    }
```

If we want to add an element to the Compound, we can use the `MojangsonCompound#put(key,value)` method:

```java
	MojangsonInt mInt = new MojangsonInt(9001);
	compound.put("Level", mInt);
```

Finally, if we want to represent the Compound into a Mojangson string:

```java
	StringBuilder sb = new StringBuilder();
	compound.write(sb);
	String mojangson = sb.toString();
```

Limitations
-----------

There are currently some limitations to this library, which are currently Work-In-Progress:

- Support spaces outside of String literals: Using a Mojangson string such as `{Name: "Momo"}` will not properly work.
