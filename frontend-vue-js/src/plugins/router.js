import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

import { getParsedToken, getToken } from '../util/token';

import User from './../pages/user/Container';
import Guest from './../pages/user/Container-guest';
import Admin from './../pages/user/Container-admin';
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
            requiresAuth: true,
            role: 'REGULAR'
        },
        children: [
            {
                component: UserLanding,
                name: 'user',
                path: ''
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
    {
        component: Guest,
        path: '/guest',
        meta: {
            guest: true
        },
        children: [
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
    {
        component: Admin,
        path: '/admin',
        meta: {
            requiresAuth: true,
            role: 'ADMINISTRATOR'
        },
        children: [
            {
                component: UserLanding,
                name: 'user',
                path: ''
            }
        ]
    },
]

function getHomePage(role) {
    if (role == 'REGULAR')
        return '/'
    else if (role == 'ADMINISTRATOR')
        return '/admin'
    else
        return '/'
}

let router = new VueRouter({
    routes
});

router.beforeEach((to, from, next) => {
    let token = getToken();

    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (token == null) {
            next({
                path: '/guest/login',
                params: { nextUrl: to.fullPath }
            })
        } else {
            let parsedToken = getParsedToken()

            if (to.matched.some(record => record.meta.role && record.meta.role == parsedToken.role)) {
                next()
            } else {
                next(getHomePage(parsedToken.role))
            }
        }
    } else if (to.matched.some(record => record.meta.guest)) {
        if (token == null) {
            next()
        }
        else {
            let parsedToken = getParsedToken()
            next(getHomePage(parsedToken.role))
        }
    } else {
        next()
    }
})

export default router
