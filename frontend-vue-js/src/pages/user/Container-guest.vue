<template>
	<v-app style="background: rgba(0,0,0,0);">
		<navbar v-bind:items="links" :logout="false"></navbar>
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
				title: 'Log in',
				to: '/guest/login',
			},
			{
				title: 'Register',
				children: [
					{
						title: 'User',
						to: '/guest/register',
					},
					{
						title: 'Agent',
						to: '/guest/aregister',
					},
				],
			},
			{
				title: 'Search',
				children: [
					{
						title: 'Users',
						to: '/guest/usersearch',
					},
					{
						title: 'Hashtags',
						to: '/guest/hashtagsearch',
					},
					{
						title: 'Locations',
						to: '/guest/locationsearch',
					},
				],
			},
		],
	}),
};
</script>
