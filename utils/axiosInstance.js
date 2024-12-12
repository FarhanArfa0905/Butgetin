const axios = require('axios');

const axiosInstance = axios.create({
    baseURL: 'https://machinelearning-60846954364.asia-southeast1.run.app',
    timeout: 5000,
});

module.exports = axiosInstance;
