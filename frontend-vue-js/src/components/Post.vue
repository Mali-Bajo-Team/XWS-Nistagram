<template>
	<v-card>
		<v-snackbar v-model="snackbar" :timeout="2000" bottom class="mb-5" right>
			{{ snackbarText }}

			<template v-slot:action="{ attrs }">
				<v-btn color="primary" text v-bind="attrs" @click="snackbar = false">
					Close
				</v-btn>
			</template>
		</v-snackbar>

		<v-card-title v-if="post.my_post">
			{{ post.my_post.creator_username }}
			<!--List of photos and videos-->
			<v-carousel
				v-if="post.my_post"
				hide-delimiter-background
				show-arrows-on-hover
				delimiter-icon="mdi-minus"
				height="auto"
			>
				<v-carousel-item v-for="(slide, i) in post.my_post.content" :key="i">
					<video
						v-if="slide.type == 'video'"
						controls
						:src="getImageUrl(slide.path)"
						class="white--text align-end"
						gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
						width="100%"
					></video>

					<v-img v-if="post.my_post" :src="getImageUrl(slide.path)"></v-img>
				</v-carousel-item>
			</v-carousel>
			<!--End of list of photos and videos-->
		</v-card-title>
		<v-card-text>
			<v-row v-if="post.my_post.is_add">
				<v-col>
					<v-btn block @click="add_clicked">
						Click add
					</v-btn>
				</v-col>
			</v-row>
			<v-row>
				<v-col>
					<!--The number of likes and comments-->
					<v-icon medium>mdi-heart</v-icon>{{ likeCount }}
					<v-icon medium right>mdi-comment</v-icon>{{ commentCount }}
				</v-col>

				<v-col class="text-right">
					<!-- Like button-->
					<v-tooltip bottom v-if="!liked && !disliked && loggedIn">
						<template v-slot:activator="{ on, attrs }">
							<v-btn
								icon
								color="gray"
								@click="likePost(post._id)"
								v-bind="attrs"
								v-on="on"
							>
								<v-icon>mdi-thumb-up</v-icon>
							</v-btn>
						</template>
						<span>Like</span>
					</v-tooltip>
					<!--End of like button-->

					<!-- UnLike button-->
					<v-tooltip bottom v-if="liked && loggedIn">
						<template v-slot:activator="{ on, attrs }">
							<v-btn
								icon
								color="primary"
								@click="unlikePost(post._id)"
								v-bind="attrs"
								v-on="on"
							>
								<v-icon>mdi-thumb-up</v-icon>
							</v-btn>
						</template>
						<span>Unlike</span>
					</v-tooltip>
					<!-- End of the  UnLike button-->

					<!-- Dislike button-->
					<v-tooltip bottom v-if="!liked && !disliked && loggedIn">
						<template v-slot:activator="{ on, attrs }">
							<v-btn
								@click="dislikePost(post._id)"
								icon
								color="gray"
								v-bind="attrs"
								v-on="on"
								><v-icon>mdi-thumb-down</v-icon>
							</v-btn>
						</template>
						<span>Dislike</span>
					</v-tooltip>

					<!-- End of the Dislike button-->

					<!-- UnDislike button-->
					<v-tooltip bottom v-if="disliked && loggedIn">
						<template v-slot:activator="{ on, attrs }">
							<v-btn
								@click="undislikePost(post._id)"
								icon
								color="error"
								v-bind="attrs"
								v-on="on"
								><v-icon>mdi-thumb-down</v-icon>
							</v-btn>
						</template>
						<span>Undislike</span>
					</v-tooltip>
					<!-- End of the UnDislike button-->

					<!--Dialog for adding comment-->
					<v-dialog max-width="600px" v-model="openedNewCommentDialog">
						<template v-slot:activator="{ on: dialog }">
							<v-tooltip bottom>
								<template v-slot:activator="{ on: tooltip }">
									<v-btn
										class="mx-2"
										fab
										x-small
										v-on="{ ...dialog, ...tooltip }"
										color="primary"
										v-if="loggedIn"
										><v-icon>mdi-plus</v-icon>
									</v-btn>
								</template>
								<span>Add comment</span>
							</v-tooltip>
						</template>
						<v-card>
							<v-card-title> Add a new comment to the post </v-card-title>
							<v-card-text>
								<!--Field for comments-->
								<v-textarea
									v-model="newCommentContent"
									outlined
									name="input-7-4"
									no-resize
									rows="3"
									label="Comment"
									clearable
									clear-icon="mdi-close-circle"
								></v-textarea>
								<!--End of field for comments-->
							</v-card-text>
							<v-card-actions>
								<!--Buttons to confirm or cancel action-->
								<v-spacer></v-spacer>
								<v-btn
									color="error"
									text
									@click="openedNewCommentDialog = false"
									>Cancel</v-btn
								>
								<v-btn
									color="primary"
									text
									@click="
										createComment(post._id), (openedNewCommentDialog = false)
									"
								>
									Confirm
								</v-btn>
							</v-card-actions>
						</v-card>
					</v-dialog>
					<!--End of dialog for adding comments-->

					<!--Dialog to report inappropriate content-->
					<v-dialog
						width="600px"
						v-model="openedReportInappropriateContentDialog"
					>
						<template v-slot:activator="{ on: dialog }">
							<v-tooltip bottom>
								<template v-slot:activator="{ on: tooltip }">
									<v-btn
										class="mx-2"
										fab
										x-small
										v-on="{ ...dialog, ...tooltip }"
										color="error"
										v-if="loggedIn"
										><v-icon>mdi-alert-octagon</v-icon>
									</v-btn>
								</template>
								<span>Report as inappropriate</span>
							</v-tooltip>
						</template>
						<!-- Dialog content -->
						<v-card>
							<v-card-title> <br /> </v-card-title>
							<v-card-text>
								<v-row>
									<v-textarea
										v-model="reportInappropriateContent.message"
										outlined
										name="input-7-4"
										no-resize
										label="Reason for report"
										clearable
										clear-icon="mdi-close-circle"
									></v-textarea>
								</v-row>
								<v-spacer></v-spacer>
								<br />
								<v-btn
									color="primary"
									@click="
										reportInappropriatePost(post),
											(openedReportInappropriateContentDialog = false)
									"
								>
									Confirm
								</v-btn>
								<v-btn
									text
									@click="openedReportInappropriateContentDialog = false"
								>
									Close
								</v-btn>
							</v-card-text>
						</v-card>
						<!-- End of the dialog content -->
					</v-dialog>
					<!--End of dialog for reporting inappropriate content-->
				</v-col>
			</v-row>

			<!--Expansion panels for showing comments-->
			<v-row
				><v-col>
					<v-expansion-panels v-show="commentCount > 0">
						<v-expansion-panel>
							<v-expansion-panel-header>
								Show comments
							</v-expansion-panel-header>
							<v-expansion-panel-content
								v-for="(comment, j) in comments"
								:key="j"
							>
								<h3>{{ comment.username }}</h3>
								{{ comment.content }}
							</v-expansion-panel-content>
						</v-expansion-panel>
					</v-expansion-panels>
				</v-col></v-row
			>
			<!--End of expansion panels-->
		</v-card-text>
	</v-card>
</template>

<script>
import { getToken, getParsedToken } from '../util/token';
import { getTodayDateString } from '../util/dateHandler';

export default {
	props: {
		post: {
			required: true,
			type: Object,
		},
	},
	data() {
		return {
			likeCount: 0,
			liked: false,
			disliked: false,
			loggedIn: false,
			snackbar: false,
			snackbarText: '',
			openedNewCommentDialog: null,
			newCommentContent: '',
			user: null,
			comments: [],
			openedReportInappropriateContentDialog: false,
			reportInappropriateContent: {
				story_id: '',
				story_creator_id: '',
				story_reporter_id: '',
				post_id: '',
				post_creator_id: '',
				post_reporter_id: '',
				time_stamp: '',
				message: '',
			},
		};
	},
	mounted() {
		if (getToken()) {
			this.loggedIn = true;

			// TODO: fix endpoints to make this unnecessary
			this.axios
				.get(
					process.env.VUE_APP_BACKEND_URL +
						process.env.VUE_APP_USER +
						process.env.VUE_APP_USER_USERNAME +
						'/' +
						getParsedToken().sub,
					{
						headers: {
							Authorization: 'Bearer ' + localStorage.getItem('JWT-CPIS'),
						},
					}
				)
				.then((res) => {
					this.user = res.data;
				});

			this.axios
				.get(
					process.env.VUE_APP_BACKEND_URL +
						process.env.VUE_APP_CONTENT_REACTION +
						this.post._id,
					{
						headers: {
							Authorization: 'Bearer ' + getToken(),
						},
					}
				)
				.then((res) => {
					if (res.data) {
						if (res.data.reaction_type == 'like') this.liked = true;
						if (res.data.reaction_type == 'dislike') this.disliked = true;
					}
				})
				.catch(() => {});

			if (this.post.comments) this.comments = this.post.comments;
			if (this.post.like_count) this.likeCount = this.post.like_count;
		}
	},
	computed: {
		commentCount: function() {
			if (!this.comments) return 0;
			else return this.comments.length;
		},
	},
	methods: {
		add_clicked() {
			window.location.href = this.post.my_post.add_link
		},
		getImageUrl(path) {
			if (path)
				return (
					process.env.VUE_APP_BACKEND_URL +
					process.env.VUE_APP_FILE_ENDPOINT +
					path
				);
			else
				return 'https://icon-library.com/images/default-user-icon/default-user-icon-4.jpg';
		},
		likePost(postID) {
			this.axios
				.post(
					process.env.VUE_APP_BACKEND_URL +
						process.env.VUE_APP_CONTENT_REACTION +
						postID,
					{
						reaction_type: 'like',
					},
					{
						headers: {
							Authorization: 'Bearer ' + getToken(),
						},
					}
				)
				.then(() => {
					this.liked = true;
					this.likeCount = this.likeCount + 1;
				})
				.catch(() => {
					this.snackbarText = 'Liking post failed.';
					this.snackbar = true;
				});
		},
		dislikePost(postID) {
			this.axios
				.post(
					process.env.VUE_APP_BACKEND_URL +
						process.env.VUE_APP_CONTENT_REACTION +
						postID,
					{
						reaction_type: 'dislike',
					},
					{
						headers: {
							Authorization: 'Bearer ' + getToken(),
						},
					}
				)
				.then(() => {
					this.disliked = true;
				})
				.catch(() => {
					this.snackbarText = 'Disliking post failed.';
					this.snackbar = true;
				});
		},
		unlikePost(postID) {
			this.axios
				.post(
					process.env.VUE_APP_BACKEND_URL +
						process.env.VUE_APP_CONTENT_UNREACTION +
						postID,
					{},
					{
						headers: {
							Authorization: 'Bearer ' + getToken(),
						},
					}
				)
				.then(() => {
					this.liked = false;
					this.likeCount = this.likeCount - 1;
				})
				.catch(() => {
					this.snackbarText = 'Unliking post failed.';
					this.snackbar = true;
				});
		},
		undislikePost(postID) {
			this.axios
				.post(
					process.env.VUE_APP_BACKEND_URL +
						process.env.VUE_APP_CONTENT_UNREACTION +
						postID,
					{},
					{
						headers: {
							Authorization: 'Bearer ' + getToken(),
						},
					}
				)
				.then(() => {
					this.disliked = false;
				})
				.catch(() => {
					this.snackbarText = 'Undisliking post failed.';
					this.snackbar = true;
				});
		},
		createComment(postID) {
			this.axios
				.post(
					process.env.VUE_APP_BACKEND_URL +
						process.env.VUE_APP_CONTENT_COMMENT +
						postID,
					{
						content: this.newCommentContent,
					},
					{
						headers: {
							Authorization: 'Bearer ' + getToken(),
						},
					}
				)
				.then(() => {
					this.comments.push({
						content: this.newCommentContent,
						username: this.user.username,
					});
				})
				.catch(() => {
					this.snackbarText = 'Adding comment failed.';
					this.snackbar = true;
				});
		},
		reportInappropriatePost(post) {
			console.log('\n\n\n\n\n\n' + this.user.username + '\n\n\n\n\n');
			this.reportInappropriateContent.post_id = post._id;
			this.reportInappropriateContent.post_reporter_id = this.user._id;
			this.reportInappropriateContent.post_reporter_username = this.user.username;
			this.reportInappropriateContent.post_creator_username =
				post.my_post.creator_username;
			this.reportInappropriateContent.time_stamp = getTodayDateString();

			this.axios
				.post(
					process.env.VUE_APP_BACKEND_URL +
						process.env.VUE_APP_INAPPROPRIATE_POST,
					this.reportInappropriateContent,
					{
						headers: {
							Authorization: 'Bearer ' + localStorage.getItem('JWT-CPIS'),
						},
					}
				)
				.then(() => {
					this.snackbarText = 'Sucessfully reported post as inappropriate.';
					this.snackbar = true;
				})
				.catch(() => {
					this.snackbarText = 'Report failed.';
					this.snackbar = true;
				});
		},
	},
};
</script>
