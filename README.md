
What is *Mojangson*?
-------
*Mojangson* is a variant of JSON, used in the voxel-based game Minecraft by Mojang. It's current use is to get a readable representation of [Data tags](http://minecraft.gamepedia.com/Commands#Data_tags), which are used to read and modify data from technical entities of the game. 

*Mojangson* is slightly different than JSON because of it's relative flexibility. For example, certain rules such as *double-quotes* for element key names are not mandatory. However, the data representation can be somewhat variable, numerical values may need an extra prefix with their literal value, which makes the use of standart JSON parsers impossible.

*Mojangson* can represent different literal values: Byte, Double, Float, Integer, Long, Short and String. The syntax also supports the use of *Compounds*, which is a nested Map with a standart key-value pair syntax, and the use of *Arrays*, which is a List of values (without a key) of the same type.

API Example - Compound
-------
This is an example of a simple Mojangson compound string:

    {Name:"Momo", Age:100s, Height:1.89d}

This compound can be broken down like this:

    { // Compound start
        Name:"Momo", // MojangsonString value, key is 'Name'
        Age:100s,     // MojangsonShort value ('s' suffix), key is 'Age'
        Height:1.89d // MojangsonDouble value ('d' suffix), key is 'Height'
    } // Compound end

In order to represent the compound, we need to invoke the `MojangsonCompound#read(String)` method:

```java
String mojangson = "{Name:\"Momo\", Age:100s, Height:1.89d}";
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

Finally, if we want to represent the Compound back into a Mojangson string:

```java
StringBuilder sb = new StringBuilder();
compound.write(sb);
String mojangson = sb.toString();
```

API Example - Arrays
------------

Arrays are a list of elements of the same type. They do not have a key-value pair element list, but rather all the values put together.

To create an Array, in this case an Array of Strings, you can call the constructor:

```java
MojangsonArray<MojangsonString> array = new MojangsonArray<>(MojangsonString.class);
```

To add elements to the Array, you can use the `List#add(MojangsonString)` method:

```java
array.add(new MojangsonString("Hello"));
array.add(new MojangsonString("World"));
```

Finally, to print the array:

```java
StringBuilder builder = new StringBuilder();
array.write(builder);
System.out.println(builder.toString()); // Prints ["Hello","World"]
```