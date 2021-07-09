<template>
	<v-row class="pa-5">
		<v-spacer></v-spacer>
		<v-col sm="8" md="6" lg="4" xl="4" cols="12">
			<v-card elevation="4" class="pa-5">
				<v-card-title>Agent Registration</v-card-title>

				<v-form>
					<v-text-field
						v-model="form.username"
						:error-messages="usernameErrors"
						label="Username"
						@blur="$v.form.username.$touch()"
						@input="$v.form.username.$touch()"
					></v-text-field>

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

					<v-select
						v-model="form.gender"
						:items="form.items"
						:error-messages="genderErrors"
						@blur="$v.form.gender.$touch()"
						@input="$v.form.gender.$touch()"
						label="Gender"
						outlined
					></v-select>

					<v-text-field
						v-model="form.linkToWebSite"
						:error-messages="linkToWebSiteErrors"
						label="Link to website"
						@blur="$v.form.linkToWebSite.$touch()"
						@input="$v.form.linkToWebSite.$touch()"
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
import { validationMixin } from 'vuelidate';
import axios from 'axios';
import { required, email, minLength, sameAs } from 'vuelidate/lib/validators';

export default {
	mixins: [validationMixin],
	data() {
		return {
			snackbar: false,
			snackbarText: '',
			loading: false,
			form: {
				items: ['Man', 'Woman', 'Others'],
				username: '',
				email: '',
				password: '',
				showPassword: false,
				repeatPassword: '',
				showRepeatPassword: false,
				name: '',
				surname: '',
				gender: '',
				linkToWebSite: '',
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
				sameAsPassword: sameAs('password'),
			},
			username: {
				required,
			},
			email: {
				required,
				email,
			},
			name: {
				required,
			},
			surname: {
				required,
			},
			linkToWebSite: {
				required,
			},
			gender: {
				required,
			},
		},
	},
	computed: {
		repeatPasswordErrors() {
			const errors = [];
			if (!this.$v.form.repeatPassword.$dirty) return errors;
			!this.$v.form.repeatPassword.required &&
				errors.push('Password is required.');
			!this.$v.form.repeatPassword.minLength &&
				errors.push('Password is too short.');
			!this.$v.form.repeatPassword.sameAsPassword &&
				errors.push('Passwords must match.');
			return errors;
		},
		passwordErrors() {
			const errors = [];
			if (!this.$v.form.password.$dirty) return errors;
			!this.$v.form.password.required && errors.push('Password is required.');
			!this.$v.form.password.minLength && errors.push('Password is too short.');
			return errors;
		},
		emailErrors() {
			const errors = [];
			if (!this.$v.form.email.$dirty) return errors;
			!this.$v.form.email.required && errors.push('Email is required.');
			!this.$v.form.email.email && errors.push('Email is not valid.');
			return errors;
		},
		nameErrors() {
			const errors = [];
			if (!this.$v.form.name.$dirty) return errors;
			!this.$v.form.name.required && errors.push('Name is required.');
			return errors;
		},
		surnameErrors() {
			const errors = [];
			if (!this.$v.form.surname.$dirty) return errors;
			!this.$v.form.surname.required && errors.push('Surname is required.');
			return errors;
		},
		linkToWebSiteErrors() {
			const errors = [];
			if (!this.$v.form.linkToWebSite.$dirty) return errors;
			!this.$v.form.linkToWebSite.required &&
				errors.push('Link To Web Site is required.');
			return errors;
		},
		usernameErrors() {
			const errors = [];
			if (!this.$v.form.username.$dirty) return errors;
			!this.$v.form.username.required && errors.push('Username is required.');
			return errors;
		},
		genderErrors() {
			const errors = [];
			if (!this.$v.form.gender.$dirty) return errors;
			!this.$v.form.gender.required && errors.push('Gender is required.');
			return errors;
		},
	},
	methods: {
		register() {
			axios
				.all([
					axios.post(
						process.env.VUE_APP_BACKEND_URL +
							process.env.VUE_APP_AGENT_REGISTRATION_ENDPOINT,
						{
							username: this.form.username,
							password: this.form.password,
							email: this.form.email,
							name: this.form.name,
							surname: this.form.surname,
							gender: this.form.gender,
							linkToWebSite: this.form.linkToWebSite
						}
					),
					axios.post(
						process.env.VUE_APP_BACKEND_URL +
							process.env.VUE_APP_CREATEUSER_CONTENTENDPOINT,
						{
							username: this.form.username,
						}
					),
					axios.post(
						process.env.VUE_APP_BACKEND_URL +
							process.env.VUE_APP_RECOMMENDATION_CREATE_USER +
							this.form.username,
						{}
					),
				])
				.then(
					axios.spread((data1, data2) => {
						this.snackbarText = 'Registration successful.';
						this.loading = false;
						this.snackbar = true;
						console.log('data1', data1, 'data2', data2);
					})
				)
				.catch((error) => {
					if (error.response && error.response.data)
						this.snackbarText = error.response.data;
					else this.snackbarText = error.message;
					this.loading = false;
					this.snackbar = true;
				});
		},
		submit() {
			this.$v.$touch();

			if (!this.$v.$invalid) {
				this.loading = true;
				this.register();
			}
		},
		reset() {
			this.$v.$reset();
			this.form.username = '';
			this.form.email = '';
			this.form.password = '';
			this.form.repeatPassword = '';
			this.form.name = '';
			this.form.surname = '';
			this.form.gender = '';
			this.form.linkToWebSite = '';
		},
	},
};
</script>
