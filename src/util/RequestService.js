import axios from 'axios';

const baseurl = 'http://localhost:8080';

const RequestService = {

    validationErrorGetter: function (axiosError) {
        if (axiosError.response.status > 499) {
            return { form: 'There was a problem with what you were trying to do.' };
        }

        const errors = axiosError.response.data.errors
        const newErrorState = {};
        errors.forEach(error => {
            console.log(error);
            newErrorState[error.field] = error.defaultMessage;
        });
        return newErrorState;
    },

    signUp: function (payload, thenCallback, catchCallback) {
        axios.post(baseurl + '/users/sign-up', payload)
            .then(thenCallback)
            .catch(catchCallback);
    },

    login: function (payload, thenCallback, catchCallback) {
        axios.post(baseurl + '/login', payload)
            .then(thenCallback)
            .catch(catchCallback);
    },

    newPrintJob(payload, thenCallback, catchCallback) {
        console.log(payload);
        axios.post(baseurl + '/api/printjobs', payload)
            .then(thenCallback)
            .catch(catchCallback);

    }
}


export default RequestService;