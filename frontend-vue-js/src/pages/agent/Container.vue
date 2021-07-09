<template>
	<v-app style="background: rgba(0,0,0,0);">
		<navbar v-bind:items="links" v-bind:logout="true"></navbar>
		<v-main>
			<v-container fluid>
				<router-view></router-view>
			</v-container>
		</v-main>
	</v-app>
</template>

<script>
import navbar from '../../components/Navbar';

export default {
	components: {
		navbar,
	},
	data: () => ({
		links: [
			{
				title: 'Feed',
				children: [
					{
						title: 'Posts',
						to: '/agent/feed/posts',
					},
					{
						title: 'Stories',
						to: '/agent/feed/stories',
					},
				],
			},
			{
				title: 'My profile',
				to: '/agent/profile',
			},
			{
				title: 'Search',
				children: [
					{
						title: 'Users',
						to: '/agent/usersearch',
					},
					{
						title: 'Hashtags',
						to: '/agent/hashtagsearch',
					},
					{
						title: 'Locations',
						to: '/agent/locationsearch',
					},
				],
			},
			{
				title: 'Recommendations',
				to: '/agent/recommendations',
			},
		],
	}),
};
</script>
