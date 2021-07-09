<template>
	<v-row class="pa-5">
		<v-spacer></v-spacer>

		<v-col sm="8" md="12" lg="4" cols="12">
			<v-row>
				<v-col>
					<v-card
						v-for="inappropriatePost in inappropriatePosts"
						:key="inappropriatePost._id"
						style="margin-bottom:3em;"
					>
						<!--Inappropriate content-->
						<v-img
							height="250"
							src="https://cdn.vuetifyjs.com/images/cards/cooking.png"
						></v-img>

						<!--Information:usernames and date about content-->
						<v-card-text>
							The owner of content:
							<b>{{ inappropriatePost.post_creator_username }}</b>
							<br />
							The person who reported this content:<b>
								{{ inappropriatePost.post_reporter_username }}
							</b>
							<br />
							Content reporting date: <b>{{ inappropriatePost.time_stamp }}</b>
						</v-card-text>

						<!--End of information about  content-->

						<v-divider class="mx-4"></v-divider>

						<!--Reason for reporting-->
						<v-card-text>
							The reason of reporting this content as inappropriate:
							<br />

							<b>{{ inappropriatePost.message }}</b>
						</v-card-text>
						<!--End of reason for reporting-->

						<!--Buttons-->
						<v-card-actions>
							<!--Button for removing content-->
							<v-btn
								class="ma-2"
								outlined
								rounded
								color="primary"
								@click="removeContent('kao kontent')"
							>
								<v-icon>
									mdi-delete-circle
								</v-icon>
								Remove content
							</v-btn>
							<!--End of button for removing content-->

							<!--Button for removing profile-->
							<v-btn
								class="ma-2"
								outlined
								rounded
								color="red"
								@click="removeProfile('kao profi')"
							>
								<v-icon>
									mdi-delete-circle
								</v-icon>
								Remove profile
							</v-btn>
							<!--End of button for removing profile-->
						</v-card-actions>
					</v-card>
				</v-col>
			</v-row>
		</v-col>
		<v-spacer></v-spacer>
	</v-row>
</template>

<script>
// TODO: ADD INAPPROPRIATE STORIES !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
export default {
	data() {
		return {
			inappropriatePosts: [],
			inappropriateStories: [],
		};
	},
	mounted() {
		this.axios
			.get(
				process.env.VUE_APP_BACKEND_URL +
					process.env.VUE_APP_INAPPROPRIATE_POST,
				{
					headers: {
						Authorization: 'Bearer ' + localStorage.getItem('JWT-CPIS'),
					},
				}
			)
			.then((response) => {
				this.inappropriatePosts = response.data;
			})
			.catch((e) => {
				console.log('Inappropriate content error: ' + e);
			});

		this.axios
			.get(
				process.env.VUE_APP_BACKEND_URL +
					process.env.VUE_APP_INAPPROPRIATE_STORY,
				{
					headers: {
						Authorization: 'Bearer ' + localStorage.getItem('JWT-CPIS'),
					},
				}
			)
			.then((response) => {
				this.inappropriateStories = response.data;
			})
			.catch((e) => {
				console.log('Inappropriate content error: ' + e);
			});
	},
	methods: {
		removeContent(content) {
			console.log('\n\n\n remove content' + content + ' \n\n\n');
		},
		removeProfile(username) {
			console.log('\n\n\n remove profile' + username + ' \n\n');
		},
	},
};
</script>
