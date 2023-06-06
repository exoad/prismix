Prismix - Color Picker
Copyright (C) Jack Meng 2023

Version:
  June 01 2023 "Closed Beta"

---
Program:
- Main class: "com.jackmeng.prismix.jm_Prismix"
- Class Path: "pkg/src"
- Required Libs.: flatlaf, flatlaf-intellij, stljackmeng, luaj (optional, unless using color pipeline)

---
Requirements:
- Java 14+
- OpenGL compatible system

___
Color Pipeline:
The color pipeline is a proposed way for users to directly create images representing different versions of their colors in different styles. These styles range from simple mesh based gradient paints to unique color animations. All of this is done via the LuaJ interop and allows for users to implement beautiful combinations of colors in whatever way they want right in the prismix program.

Why Lua? Lua is a simple language and can be interoped to implement many functions. In this pipeline program, Lua can almost be treated as the relationship or GLSL, but instead is just an abstraction ontop of the Java Layer for fast implementation and fast result.

Why?

This pipeline helps to create a "MeshedColor" which can contain multiple colors or can be in multiple forms. By utilizing this pipeline, users can create shapes, gradients, texts and more with just Lua and many functions in order to visualize your color palette.

Furthermore, all of these generated Lua can be combined into Java bytecode (via LuaJ's awesome bytecode compiler) and linked with the Pipeline standalone library to be easily integrated in your projects (maybe even outside of the Swing/AWT ecosystem!).
