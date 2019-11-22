/**
 * Rules I am interested in:
 * 1. writing a request that has nothing to do with auth shouldn't require any knowledge or interaction with auth
 * 2. Auth issues should automatically resolve themselves somehow.
 *    For example, if you receive a token expired kind of error, the app should automatically "log you out" and require you to log in again.
 * 3. State should maybe be persisted at some level if this happens. It would not be a nice experience to get logged out and whatever form you were filling out is now blank.
 * 4. We will have to be careful about any global axios settings, because the front-end may hit other api's.
 *
 *
 * Back end notes: We will need specific errors to diferentiate between lack of permissions and lack of a valid token.
 *
 * There's a weird issue where you can upload a file and a new entry is created in the database,
 * but you still get a 500 error because the file upload failed.
 *
 * Instructions for creating a new request to the back end api:
 * 1. Run generateApiInstance to get an axios instance and assign it to a variable.
 * 2. Call that variable and pass it the url (combination of backendURL and the rest of the path, which you will find in the relevant controller file)
 * 3. And as the 2nd and 3rd arguments pass it a callback that will allow your component to respond accordingly to the response
 */
import axios from 'axios';

const backendUrl = 'http://localhost:8080';

// This is for use with our Java API
function generateApiInstance() {
  const instance = axios.create({
    baseURL: backendUrl,
    headers: {
      Authorization: RequestService.requestState.token,
    },
  });
  return instance;
}

const RequestService = {
  requestState: {
    token: '',
  },

  validationErrorGetter: function(axiosError) {
    if (axiosError.response.status > 499) {
      return { form: 'There was a problem with what you were trying to do.' };
    }

    // Temporary, I don't like it but it saves us a crash for the moment.
    if (axiosError.response.status === 403) {
      return { form: 'Please try logging out and logging in.' };
    }

    if (axiosError.response.status < 499 && axiosError.response.data) {
      return { form: axiosError.response.data.errorDescription };
    }

    const errors = axiosError.response.data.errors;
    if (!errors) {
      return { form: 'There was a problem with what you were trying to do.' };
    }
    const newErrorState = {};
    errors.forEach(error => {
      newErrorState[error.field] = error.defaultMessage;
    });
    return newErrorState;
  },

  signUp: function(payload, thenCallback, catchCallback) {
    const backendInstance = generateApiInstance();
    backendInstance
      .post(backendUrl + '/users/sign-up', payload)
      .then(thenCallback)
      .catch(catchCallback);
  },

  login: function(payload, thenCallback, catchCallback) {
    axios
      .post(backendUrl + '/login', payload)
      .then(thenCallback)
      .catch(catchCallback);
  },

  newPrintJob(payload, thenCallback, catchCallback) {
    const backendInstance = generateApiInstance();
    backendInstance
      .post(backendUrl + '/api/print-jobs', payload)
      .then(thenCallback)
      .catch(catchCallback);
  },

  getPrintJobs(thenCallback, catchCallback) {
    const backendInstance = generateApiInstance();
    backendInstance
      .get(backendUrl + '/api/print-jobs')
      .then(thenCallback)
      .catch(catchCallback);
  },

  saveCard(payload, thenCallback, catchCallback) {
    const backendInstance = generateApiInstance();
    const cardId = payload.id;
    backendInstance
      .put(backendUrl + `/api/print-jobs/${cardId}`, payload)
    },

  getUsers(thenCallback, catchCallback) {
    const backendInstance = generateApiInstance();
    backendInstance
      .get(backendUrl + '/users')
      .then(thenCallback)
      .catch(catchCallback);
  },

  getActiveColors(thenCallback, catchCallback) {
    const backendInstance = generateApiInstance();
    backendInstance
      .get(backendUrl + '/colors')
      .then(thenCallback)
      .catch(catchCallback);  
  },
};

export default RequestService;
