const path = require('path');

const config = {
  mode: 'development',
  entry: [
    './src/index.js',
  ],
  module: {
    rules: [
      {
        test: /\.js$/,
        loaders: [
          'babel-loader',
        ],
        exclude: /node_modules/,
      },
      {
        test: /\.css$/,
        loaders: [
          'style-loader',
          'css-loader',
        ]
      },
    ],
  },
  devServer: {
    port: 8000,
    proxy: {
      "/devices": "http://localhost:8080"
    }
  }
};

module.exports = config;