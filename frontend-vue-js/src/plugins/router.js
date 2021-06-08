import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

import User from './../pages/user/Container';
import UserLanding from './../pages/user/landing/Landing';
import Login from './../pages/user/login/Login';
import Registration from './../pages/user/registration/Registration';
import Profile from './../pages/user/profile/Profile';
import UserSearch from './../pages/user/user-search/UserSearch';
import HashtagSearch from './../pages/user/post-search/HashtagSearch';
import LocationSearch from './../pages/user/post-search/LocationSearch';
import OtherProfile from './../pages/user/profile/OtherProfile';


const routes = [
    {
        component: User,
        path: '/',
        meta: {
            user: true
        },
        children: [
            {
                component: UserLanding,
                name: 'user',
                path: ''
            },
            {
                component: Login,
                name: 'login',
                path: 'login'
            },
            {
                component: Registration,
                name: 'registration',
                path: 'register'
            },
            {
                component:Profile,
                name:'profile',
                path:'profile'
            },
            {
                component:UserSearch,
                name:'usersearch',
                path:'usersearch'
            },
            {
                component:HashtagSearch,
                name:'hashtagsearch',
                path:'hashtagsearch'
            },
            {
                component:LocationSearch,
                name:'locationsearch',
                path:'locationsearch'
            },
            {
                component: OtherProfile,
                name: 'userProfile',
                path: 'user/:username',
                props: true
            }
        ]
    },
   
    
]

let router = new VueRouter({
    routes
});

export default router
