//TODO menuler buradan ayarlanıyor
const state = {
  mini: false,
  menuItems: [
    {
      title: "My Todo App.",
    },
    {
      title: "Todos",
      icon: "mdi-content-paste",
      url: "/todos",
    },
    {
      title: "Completed",
      icon: "mdi-clipboard-check",
      url: "/completed-todos",
    },
    {
      title: "My Groups",
      icon: "mdi-content-duplicate",
      url: "/groups",
    },

  ],
};

const getters = {
  getMini(state) {
    return state.mini;
  },
  getMenuItems(state) {
    return state.menuItems;
  },
};

const mutations = {
  SET_MINI(state) {
    state.mini = !state.mini;
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  getters,
};
