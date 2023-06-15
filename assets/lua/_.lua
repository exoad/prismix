-- Software created by Jack Meng (AKA exoad). Licensed by the included "LICENSE" file. If this file is not found, the project is fully copyrighted.

print("LuaJ Bind ready!")

--[[
  To let the Prismix engine know about this script and how to configure it and such, this script must define
  a function of name:
    "_EXPORT_PRISMIX_"
]]
function _EXPORT_PRISMIX_()
  return
  {
    version="1.0", -- Version of this script
    name="Internal Load", -- Tells the Prismix reader what this script is called (The name does not have to be unique, but it must be non nil)
    author="Jack Meng", -- Not required
    targets={ -- These are based on the version scheme of jm_Prismix.java#_VERSION_, to support all versions, set this to nil (not safe)
      "2023_06_01L"
    }
  }
end