const toastPlugin = {
  install: (Vue, { store }) => {
    if (!store) {
      throw new Error("Something Went Wrong.");
    }

    Vue.prototype.$toast = {
      setMessage: function (content, type) {
        store.commit(
          "SET_MESSAGE",
          { content: content, type: type },
          { root: true }
        );
      },
    };
  },
};
export default toastPlugin;
