const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = ({ mode }) => {
    return {
        mode,
        plugins: [
            new HtmlWebpackPlugin({
                template: './src/index.html'
            })
        ],
        devtool: mode === 'development' ? 'source-map' : 'none'
    }
};