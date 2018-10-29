module.exports = {
  presets: ["@vue/app"],
  // "plugins": [
  //   [
  //     "import",
  //     {
  //       "libraryName": "iview",
  //       "libraryDirectory": "src/components"
  //     }
  //   ]
  // ]
  plugins:[
    [
      "import",
      {
          "libraryName": "JTopo",
          "libraryDirectory": "src",
          "style": true
        }
   ],
  ]
};
