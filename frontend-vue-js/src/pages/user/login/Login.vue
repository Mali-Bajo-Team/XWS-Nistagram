<template>
  <v-row class="pa-5">
    <v-spacer></v-spacer>
    <v-col sm="8" md="6" lg="4" xl="4" cols="12">
      <v-card elevation="4" class="pa-5">
        <v-card-title>Log in</v-card-title>

        <v-form>
          <v-text-field
            v-model="form.email"
            :error-messages="emailErrors"
            label="Email"
            @blur="$v.form.email.$touch()"
            @input="$v.form.email.$touch()"
          ></v-text-field>

          <v-text-field
            v-model="form.password"
            :append-icon="form.showPassword ? 'mdi-eye' : 'mdi-eye-off'"
            :errorMessages="passwordErrors"
            :type="form.showPassword ? 'text' : 'password'"
            label="Password"
            @click:append="form.showPassword = !form.showPassword"
            @blur="$v.form.password.$touch()"
            @input="$v.form.password.$touch()"
          ></v-text-field>

          <v-card-actions class="pt-5">
            <v-btn color="secondary" text @click="reset"> Reset form </v-btn>
            <v-spacer></v-spacer>
            <v-btn color="primary" @click="submit"> Log in </v-btn>
          </v-card-actions>
        </v-form>

        <v-overlay
          :absolute="true"
          :value="loading"
          :opacity="0.7"
          color="#ffffff"
        >
          <v-progress-circular
            indeterminate
            color="secondary"
          ></v-progress-circular>
        </v-overlay>

        <v-snackbar
          v-model="snackbar"
          :timeout="2000"
          bottom
          class="mb-5"
          right
        >
          {{ snackbarText }}

          <template v-slot:action="{ attrs }">
            <v-btn
              color="primary"
              text
              v-bind="attrs"
              @click="snackbar = false"
            >
              Close
            </v-btn>
          </template>
        </v-snackbar>
      </v-card>
    </v-col>

    <v-spacer></v-spacer>
  </v-row>
</template>

<script>
import { validationMixin } from "vuelidate";
import { required, email, minLength } from "vuelidate/lib/validators";

import { saveToken } from "./../../../util/token"

export default {
  mixins: [validationMixin],
  data() {
    return {
      snackbar: false,
      snackbarText: "",
      loading: false,
      form: {
        email: "",
        password: "",
        showPassword: false,
      },
    };
  },
  validations: {
    form: {
      password: {
        required,
        minLength: minLength(6),
      },
      email: {
        required,
        email,
      },
    },
  },
  computed: {
    passwordErrors() {
      const errors = [];
      if (!this.$v.form.password.$dirty) return errors;
      !this.$v.form.password.required && errors.push("Password is required.");
      !this.$v.form.password.minLength && errors.push("Password is too short.");
      return errors;
    },
    emailErrors() {
      const errors = [];
      if (!this.$v.form.email.$dirty) return errors;
      !this.$v.form.email.required && errors.push("Email is required.");
      !this.$v.form.email.email && errors.push("Email is not valid.");
      return errors;
    },
  },
  methods: {
    auth() {
      this.axios
        .post(
          process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_LOGIN_ENDPOINT,
          {
            password: this.form.password,
            email: this.form.email,
          }
        )
        .then((response) => {
          let token = response.data.accessToken;
          saveToken(token)

          if(this.$route.params.nextUrl != null){
              this.$router.push(this.$route.params.nextUrl)
          }
          else {
              this.$router.push('/')
          }
        })
        .catch((error) => {
          if (error.response && error.response.data && error.response.data.message)
            this.snackbarText = error.response.data.message;
          else if (error.message) 
            this.snackbarText = error.message;
          else
            this.snackbarText = "An unknown error has occured."
          this.loading = false;
          this.snackbar = true;
        });
    },
    submit() {
      this.$v.$touch();

      if (!this.$v.$invalid) {
        this.loading = true;
        this.auth();
      }
    },
    reset() {
      console.log(process.env.VUE_APP_BACKEND_URL);
      this.$v.$reset();
      this.form.email = "";
      this.form.password = "";
    },
  },
};
</script>