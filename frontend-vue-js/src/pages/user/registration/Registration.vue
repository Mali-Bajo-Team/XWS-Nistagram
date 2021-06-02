<template>
    <v-row class="pa-5">
    <v-spacer></v-spacer>
    <v-col sm="8" md="6" lg="4" xl="4" cols="12">
        <v-card
        elevation="4"
        class="pa-5"
        >

        <v-card-title>Register</v-card-title>

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

          <v-text-field
            v-model="form.repeatPassword"
            :append-icon="form.showRepeatPassword ? 'mdi-eye' : 'mdi-eye-off'"
            :errorMessages="repeatPasswordErrors"
            :type="form.showRepeatPassword ? 'text' : 'password'"
            label="Confirm password"
            @click:append="form.showRepeatPassword = !form.showRepeatPassword"            
            @blur="$v.form.repeatPassword.$touch()"
            @input="$v.form.repeatPassword.$touch()"
          ></v-text-field>    

            <v-text-field
            v-model="form.name"
            :error-messages="nameErrors"
            label="Name"
            @blur="$v.form.name.$touch()"
            @input="$v.form.name.$touch()"
            ></v-text-field> 

            <v-text-field
            v-model="form.surname"
            :error-messages="surnameErrors"
            label="Surname"
            @blur="$v.form.surname.$touch()"
            @input="$v.form.surname.$touch()"
            ></v-text-field>      
        
            <v-text-field
            v-model="form.address"
            :error-messages="addressErrors"
            label="Home address"
            @blur="$v.form.address.$touch()"
            @input="$v.form.address.$touch()"
            ></v-text-field> 

            <v-text-field
            v-model="form.city"
            :error-messages="cityErrors"
            label="City"
            @blur="$v.form.city.$touch()"
            @input="$v.form.city.$touch()"
            ></v-text-field> 

            <v-text-field
            v-model="form.country"
            :error-messages="countryErrors"
            label="Country"
            @blur="$v.form.country.$touch()"
            @input="$v.form.country.$touch()"
            ></v-text-field> 
            
            <v-text-field
            v-model="form.phone"
            :error-messages="phoneErrors"
            label="Phone number"
            @blur="$v.form.phone.$touch()"
            @input="$v.form.phone.$touch()"
            ></v-text-field> 

            <v-card-actions class="pt-5">
                <v-btn color="secondary" text @click="reset">
                    Reset form
                </v-btn>
                <v-spacer></v-spacer>
                <v-btn color="primary" @click="submit">
                    Register
                </v-btn>
            </v-card-actions>
        </v-form>

        <v-overlay
          :absolute="true"
          :value="loading"
          :opacity="0.7"
          color='#ffffff'
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
        right
        class="mb-5"       
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
import {
  required,
  email,
  minLength,
  sameAs,
  numeric
} from "vuelidate/lib/validators";

export default {
  mixins: [validationMixin],
  data() {
    return {
      snackbar: false,
      snackbarText: '',
      loading: false,
      form: {
        email: "",
        password: "",
        showPassword: false,
        repeatPassword: "",
        showRepeatPassword: false,
        name: "",
        surname: "",
        address: "",
        city: "",
        country: "",
        phone: ""
      },
    };
  },
  validations: {
    form: {
      password: {
        required,
        minLength: minLength(6),
      },
      repeatPassword: {
        required,
        minLength: minLength(6),
        sameAsPassword: sameAs('password')
      },
      email: {
        required,
        email,
      },
      name: {
        required
      },
      surname: {
        required
      },
      address: {
        required
      },
      city: {
        required
      },
      country: {
        required
      },
      phone: {
        required,
        numeric
      }
    },
  },
  computed: {
      repeatPasswordErrors() {
        const errors = []
        if (!this.$v.form.repeatPassword.$dirty) return errors
        !this.$v.form.repeatPassword.required && errors.push('Password is required.')        
        !this.$v.form.repeatPassword.minLength && errors.push('Password is too short.')      
        !this.$v.form.repeatPassword.sameAsPassword && errors.push('Passwords must match.')
        return errors
      },
      passwordErrors() {
        const errors = []
        if (!this.$v.form.password.$dirty) return errors
        !this.$v.form.password.required && errors.push('Password is required.')        
        !this.$v.form.password.minLength && errors.push('Password is too short.')
        return errors
      },
      emailErrors() {
        const errors = []
        if (!this.$v.form.email.$dirty) return errors
        !this.$v.form.email.required && errors.push('Email is required.')        
        !this.$v.form.email.email && errors.push('Email is not valid.')
        return errors
      },
      nameErrors() {
        const errors = []
        if (!this.$v.form.name.$dirty) return errors
        !this.$v.form.name.required && errors.push('Name is required.') 
        return errors
      },
      surnameErrors() {
        const errors = []
        if (!this.$v.form.surname.$dirty) return errors
        !this.$v.form.surname.required && errors.push('Surname is required.') 
        return errors
      },
      cityErrors() {
        const errors = []
        if (!this.$v.form.city.$dirty) return errors
        !this.$v.form.city.required && errors.push('City is required.') 
        return errors
      },
      countryErrors() {
        const errors = []
        if (!this.$v.form.country.$dirty) return errors
        !this.$v.form.country.required && errors.push('Country is required.') 
        return errors
      },
      addressErrors() {
        const errors = []
        if (!this.$v.form.address.$dirty) return errors
        !this.$v.form.address.required && errors.push('Home address is required.') 
        return errors
      },
      phoneErrors() {
        const errors = []
        if (!this.$v.form.phone.$dirty) return errors
        !this.$v.form.phone.required && errors.push('Phone number is required.') 
        !this.$v.form.phone.numeric && errors.push('Phone number should only contain numbers.') 
        return errors
      }
  },
  methods: {
    register() {
        this.axios
        .post(process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_REGISTRATION_ENDPOINT, {
          password: this.form.password,
          email: this.form.email,
          name: this.form.name,
          surname: this.form.surname,
          city: this.form.city,
          address: this.form.address,
          country: this.form.country,
          mobile : this.form.phone,
        })
        .then(() => {
          this.snackbarText = "Registration successful."
          this.loading = false
          this.snackbar = true
        })
        .catch(error => {
          if (error.response && error.response.data)
            this.snackbarText = error.response.data
          else
            this.snackbarText = error.message
          this.loading = false
          this.snackbar = true
        });
    },
    submit() {
      this.$v.$touch();

      if (!this.$v.$invalid) {
        this.loading = true
        this.register();
      }
    },
    reset() {
        this.$v.$reset()
        this.form.email = ''
        this.form.password = ''
        this.form.repeatPassword = ''
        this.form.name = ''
        this.form.surname = ''
        this.form.address = ''
        this.form.city = ''
        this.form.country = ''
        this.form.phone = ''
    }
  },
};
</script>