import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

import { getParsedToken, getToken } from '../util/token';

import User from './../pages/user/Container';
import Influencer from './../pages/user/Container-influencer';
import Agent from './../pages/agent/Container';
import Guest from './../pages/user/Container-guest';
import Admin from './../pages/admin/Container-admin';
import Verifications from './../pages/admin/Verifications';
import Login from './../pages/user/login/Login';
import Registration from './../pages/user/registration/Registration';
import AgentRegistration from './../pages/agent/registration/AgentRegistration';
import Profile from './../pages/user/profile/Profile';
import UserSearch from './../pages/user/user-search/UserSearch';
import HashtagSearch from './../pages/user/post-search/HashtagSearch';
import LocationSearch from './../pages/user/post-search/LocationSearch';
import OtherProfile from './../pages/user/profile/OtherProfile';
import PostFeed from './../pages/user/feed/PostFeed';
import StoryFeed from './../pages/user/feed/StoryFeed';
import InappropriateContent from './../pages/admin/InappropriateContent';
import AgentRegistrationRequests from './../pages/admin/agentRequests/AgentRegistrationRequests';
import RecommendedFollowers from './../pages/user/recommendations/RecommendedFollowers';

const routes = [
	{
		component: User,
		path: '/',
		meta: {
			requiresAuth: true,
			role: 'REGULAR',
		},
		children: [
			{
				component: PostFeed,
				name: 'postfeed',
				path: 'feed/posts',
			},
			{
				component: StoryFeed,
				name: 'storyfeed',
				path: 'feed/stories',
			},
			{
				component: Profile,
				name: 'profile',
				path: 'profile',
			},
			{
				component: UserSearch,
				name: 'usersearch',
				path: 'usersearch',
			},
			{
				component: HashtagSearch,
				name: 'hashtagsearch',
				path: 'hashtagsearch',
			},
			{
				component: LocationSearch,
				name: 'locationsearch',
				path: 'locationsearch',
			},
			{
				component: OtherProfile,
				name: 'userProfile',
				path: 'user/:username',
				props: true,
			},

			{
				component: RecommendedFollowers,
				name: 'recommendations',
				path: 'recommendations',
			},
		],
	},
	{
		component: Influencer,
		path: '/influencer',
		meta: {
			requiresAuth: true,
			role: 'INFLUENCER',
		},
		children: [
			{
				component: PostFeed,
				name: 'ipostfeed',
				path: 'feed/posts',
			},
			{
				component: StoryFeed,
				name: 'istoryfeed',
				path: 'feed/stories',
			},
			{
				component: Profile,
				name: 'iprofile',
				path: 'profile',
			},
			{
				component: UserSearch,
				name: 'iusersearch',
				path: 'usersearch',
			},
			{
				component: HashtagSearch,
				name: 'ihashtagsearch',
				path: 'hashtagsearch',
			},
			{
				component: LocationSearch,
				name: 'ilocationsearch',
				path: 'locationsearch',
			},
			{
				component: OtherProfile,
				name: 'iuserProfile',
				path: 'user/:username',
				props: true,
			},

			{
				component: RecommendedFollowers,
				name: 'irecommendations',
				path: 'recommendations',
			},
		],
	},
	{
		component: Agent,
		path: '/agent',
		meta: {
			requiresAuth: true,
			role: 'AGENT',
		},
		children: [
			{
				component: PostFeed,
				name: 'apostfeed',
				path: 'feed/posts',
			},
			{
				component: StoryFeed,
				name: 'astoryfeed',
				path: 'feed/stories',
			},
			{
				component: Profile,
				name: 'aprofile',
				path: 'profile',
			},
			{
				component: UserSearch,
				name: 'ausersearch',
				path: 'usersearch',
			},
			{
				component: HashtagSearch,
				name: 'ahashtagsearch',
				path: 'hashtagsearch',
			},
			{
				component: LocationSearch,
				name: 'alocationsearch',
				path: 'locationsearch',
			},
			{
				component: OtherProfile,
				name: 'auserProfile',
				path: 'user/:username',
				props: true,
			},

			{
				component: RecommendedFollowers,
				name: 'arecommendations',
				path: 'recommendations',
			},
		],
	},
	{
		component: Guest,
		path: '/guest',
		meta: {
			guest: true,
		},
		children: [
			{
				component: Login,
				name: 'login',
				path: 'login',
			},
			{
				component: Registration,
				name: 'registration',
				path: 'register',
			},
			{
				component: AgentRegistration,
				name: 'aregistration',
				path: 'aregister',
			},
			{
				component: UserSearch,
				name: 'gusersearch',
				path: 'usersearch',
			},
			{
				component: HashtagSearch,
				name: 'ghashtagsearch',
				path: 'hashtagsearch',
			},
			{
				component: LocationSearch,
				name: 'glocationsearch',
				path: 'locationsearch',
			},
			{
				component: OtherProfile,
				name: 'guserProfile',
				path: 'user/:username',
				props: true,
			},
		],
	},
	{
		component: Admin,
		path: '/admin',
		meta: {
			requiresAuth: true,
			role: 'ADMINISTRATOR',
		},
		children: [
			{
				component: Verifications,
				name: 'verifications',
				path: 'verifications',
			},
			{
				component: InappropriateContent,
				name: 'inappropriatecontents',
				path: 'inappropriatecontents',
			},
			{
				component: AgentRegistrationRequests,
				name: 'arequests',
				path: 'arequests',
			},
			{
				component: OtherProfile,
				name: 'auserProfile',
				path: 'user/:username',
				props: true,
			},
		],
	},
];

function getHomePage(role) {
	if (role == 'REGULAR') return '/feed/posts';
	else if (role == 'ADMINISTRATOR') return '/admin/verifications';
	else if (role == 'AGENT') return '/agent/feed/posts';
	else if (role == 'INFLUENCER') return '/influencer/feed/posts';
	else return '/';
}

let router = new VueRouter({
	routes,
});

router.beforeEach((to, from, next) => {
	let token = getToken();

	if (to.matched.some((record) => record.meta.requiresAuth)) {
		if (token == null) {
			next({
				path: '/guest/login',
				params: { nextUrl: to.fullPath },
			});
		} else {
			let parsedToken = getParsedToken();

			if (
				to.matched.some(
					(record) => record.meta.role && record.meta.role == parsedToken.role
				)
			) {
				next();
			} else {
				next(getHomePage(parsedToken.role));
			}
		}
	} else if (to.matched.some((record) => record.meta.guest)) {
		if (token == null) {
			next();
		} else {
			let parsedToken = getParsedToken();
			next(getHomePage(parsedToken.role));
		}
	} else {
		next();
	}
});

export default router;
