<template>
	<v-row class="pa-5">
		<v-spacer></v-spacer>

		<v-col sm="8" md="12" lg="4" cols="12">
			<v-row>
				<v-col>
					<v-card>
						<!--Information:usernames and date about content-->
						<v-card-text>
							Valid email:
							<b>ivkica@gmail.com</b>
							<br />
							The person who created this request:<b> username</b>
							<br />
							Link to the web site: <b> https://stagod/samo/da/radi</b>
						</v-card-text>

						<!--End of information about  content-->

						<v-divider class="mx-4"></v-divider>

						<!--Buttons-->
						<v-card-actions>
							<!--Button for removing content-->
							<v-btn class="ma-2" outlined rounded color="primary">
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
