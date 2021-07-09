<template>
	<v-row class="pa-5">
		<v-spacer></v-spacer>

		<v-col sm="8" md="12" lg="4" cols="12">
			<v-row>
				<v-col>
					<v-card
						v-for="request in requests"
						:key="request.username"
						style="margin:1em;"
					>
						<!--Information:usernames and date about content-->
						<v-card-text>
							Valid email:
							<b>{{ request.email }}</b>
							<br />
							The person who created this request:<b> {{ request.username }}</b>
							<br />
							Link to the web site: <b> {{ request.linkToWebSite }}</b>
						</v-card-text>

						<!--End of information about  content-->

						<v-divider class="mx-4"></v-divider>

						<!--Buttons-->
						<v-card-actions>
							<!--Button for removing content-->
							<v-btn
								class="ma-2"
								outlined
								rounded
								color="primary"
								@click="approveRequest(request)"
							>
								<v-icon>
									mdi-check-circle
								</v-icon>
								APPROVE request
							</v-btn>
							<!--End of button for removing content-->
						</v-card-actions>
					</v-card>
				</v-col>
			</v-row>
		</v-col>
		<v-spacer></v-spacer>
	</v-row>
</template>

<script>
export default {
	data() {
		return {
			requests: [],
		};
	},
	mounted() {
		this.axios
			.get(
				process.env.VUE_APP_BACKEND_URL + process.env.VUE_APP_AGENT_REQUEST,
				{
					headers: {
						Authorization: 'Bearer ' + localStorage.getItem('JWT-CPIS'),
					},
				}
			)
			.then((response) => {
				this.requests = response.data;
				console.log(this.requests);
			})
			.catch((error) => {
				console.log(error);
			});
	},
	methods: {
		approveRequest(request) {
			this.axios
				.post(
					process.env.VUE_APP_BACKEND_URL +
						process.env.VUE_APP_AGGENT_ACCEPT_REGISTRATION,
					{
						username: request.username,
					}
				)
				.then((res) => {
					console.log(res);
					this.requests.filter(
						(tempRequest) => tempRequest.username !== request.username
					);
					location.reload();
				})
				.catch((e) => {
					console.log(e);
				});
		},
	},
};
</script>
