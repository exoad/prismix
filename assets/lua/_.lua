-- Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

-- this is the defactor Lua script loaded before any other scripts are loaded
io.write("LuaJ Bind ready!\nCopyright (C) 2023 Jack Meng (exoad)\nLoading from: " + package.path)

local module_runtime = require("assets.h")
for t in module_runtime.PRISMIX_COLOR_VALUES do
  io.write(t)
end