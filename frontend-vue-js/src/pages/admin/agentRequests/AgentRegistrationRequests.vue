<template>
	<v-row class="pa-5">
		<v-spacer></v-spacer>

		<v-col sm="8" md="12" lg="4" cols="12">
			<v-row>
				<v-col>
					<v-card>
						<!--Information:usernames and date about content-->
						<v-card-text>
							The owner of content:
							<b>ivkica@gmail.com</b>
							<br />
							The person who reported this content:<b>ancica@gmail.com</b>
							<br />
							Content reporting date: <b>12-05-2021</b>
						</v-card-text>

						<!--End of information about  content-->

						<v-divider class="mx-4"></v-divider>

						<!--Reason for reporting-->
						<v-card-text>
							The reason of reporting this content as inappropriate:
							<br />

							<b>I dont like mentioning nations and racism</b>
						</v-card-text>
						<!--End of reason for reporting-->

						<!--Buttons-->
						<v-card-actions>
							<!--Button for removing content-->
							<v-btn class="ma-2" outlined rounded color="primary">
								<v-icon>
									mdi-delete-circle
								</v-icon>
								Remove content
							</v-btn>
							<!--End of button for removing content-->

							<!--Button for removing profile-->
							<v-btn class="ma-2" outlined rounded color="red">
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
