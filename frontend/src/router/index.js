import Vue from "vue";
import VueRouter from "vue-router";
import store from "@/store";

Vue.use(VueRouter);

const routes = [
  {
    path: "/auth",
    redirect: "/login",
    component: () => import("@/layouts/FullPage"),
    children: [
      {
        path: "/login",
        name: "login",
        meta: {
          layout: "layout-full",
          title: "Login",
        },
        component: () => import("@/views/auth/Auth.vue"),
      },

    ],
  },
  {
    path: "",
    redirect: "/todos",
    component: () => import("@/layouts/Main"),
    children: [
      {
        path: "/groups",
        name: "Groups",
        meta: {
          layout: "main",
        },
        component: () => import("@/views/groups/groups.vue"),
      },
      {
        path: "/todos",
        name: "Todos",
        meta: {
          layout: "main",
        },
        component: () => import("@/views/todos/todos.vue"),
      },      {
        path: "/completed-todos",
        name: "Completed Todos",
        meta: {
          layout: "main",
        },
        component: () => import("@/views/todos/completedtodos.vue"),
      },
      {
        path: "/",
        name: "Todos2",
        meta: {
          layout: "main",
        },
        component: () => import("@/views/todos/todos.vue"),
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  // redirect to login page if not logged in and trying to access a restricted page
  const loggedIn = localStorage.getItem("key");
  console.log(to.path)
  if ( !loggedIn && to.path !== "/login") {
    return next("/login");
  }
  next();
});

export default router;
