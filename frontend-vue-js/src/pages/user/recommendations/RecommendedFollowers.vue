<template>
	<v-row class="pa-5">
		<v-spacer></v-spacer>

		<v-col sm="8" md="6" lg="4" xl="4" cols="12">
			<v-card class="mx-auto" tile>
				<v-list>
					<v-list-item-group color="primary">
						<v-list-item
							v-for="(user, i) in users"
							:key="i"
							@click="userClicked(user)"
						>
							<!--Profile picture-->
							<v-list-item-avatar>
								<v-img :src="user.profileImagePath"></v-img>
							</v-list-item-avatar>
							<!--End of profile picture-->

							<!--username-->
							<v-list-item-content>
								<v-list-item-title v-text="user.username">
									<br />
								</v-list-item-title>
							</v-list-item-content>
						</v-list-item>
					</v-list-item-group>
				</v-list>
			</v-card>
		</v-col>

		<v-spacer></v-spacer>
	</v-row>
</template>
<script>
import { getUsernameFromToken, getToken } from '../../../util/token';
export default {
	data() {
		return {
			snackbar: false,
			snackbarText: '',
			loading: false,
			search: '',
			users: [],
		};
	},
	methods: {
		userClicked(user) {
			if (getToken()) this.$router.push('/user/' + user.username);
			else this.$router.push('/guest/user/' + user.username);
		},
	},
	mounted() {
		console.log(getUsernameFromToken(localStorage.getItem('JWT-CPIS')));
		this.axios
			.get(
				process.env.VUE_APP_BACKEND_URL +
					process.env.VUE_APP_RECOMMENDATION_GET_RECOMMENDATION +
					getUsernameFromToken(localStorage.getItem('JWT-CPIS')),
				{
					headers: {
						Authorization: 'Bearer ' + localStorage.getItem('JWT-CPIS'),
					},
				}
			)
			.then((response) => {
				console.log('\n\n\n\t\t POCETAK ispis preporuka \n\n');
				console.log(response);
				this.users = [];

				for (let recommendedUser of response.data) {
					// get that user from user service
					this.axios
						.get(
							process.env.VUE_APP_BACKEND_URL +
								process.env.VUE_APP_SEARCH_USERS_ENDPOINT +
								recommendedUser.recommendedId
						)
						.then((res) => {
							let realUser = res.data[0];
							if (realUser.profileImagePath) {
								realUser.profileImagePath =
									process.env.VUE_APP_BACKEND_URL +
									process.env.VUE_APP_FILE_ENDPOINT +
									realUser.profileImagePath;
							} else {
								realUser.profileImagePath =
									'https://icon-library.com/images/default-user-icon/default-user-icon-4.jpg';
							}
							this.users.push(realUser);
						})
						.catch((e) => {
							console.log('Error: Recommended Followers' + e);
						});
				}

				console.log('\n\n\n\t\t KRAJ ispis preporuka \n\n');
			})
			.catch((error) => {
				console.log(error);
			});
	},
};
</script>
