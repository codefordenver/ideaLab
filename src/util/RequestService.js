import axios from 'axios';

const baseurl = 'http://localhost:8080';

const RequestService = {

    validationErrorGetter: function (axiosError) {
        const errors = axiosError.response.data.errors
        return errors;
    },

    signUp: function (payload, thenCallback, catchCallback) {
        axios.post(baseurl + '/users/sign-up', payload)
            .then(thenCallback)
            .catch(catchCallback);
    },

    newPrintJob(payload, thenCallback, catchCallback){
        console.log(payload);
        axios.post(baseurl + '/api/printjobs', payload)
            .then(thenCallback)
            .catch(catchCallback);

    }
}


export default RequestService;