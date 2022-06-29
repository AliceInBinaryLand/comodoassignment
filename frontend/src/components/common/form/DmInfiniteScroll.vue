<template>
  <div v-intersect="onIntersect">
    <slot :item="item"></slot>
  </div>
</template>

<script>
export default {
  name: "DmInfiniteScroll",
  props: {
    item: { type: Object },
    options: { type: Object },
    totalItems: { type: Number },
    items: { type: Array },
  },
  methods: {
    onIntersect(entries, observer) {
      if (
        entries[0].isIntersecting &&
        this.item.id === this.items.slice(-1).pop().id &&
        this.items.length < this.totalItems
      ) {
        this.$emit("loadMore");
      }
    },
  },
};
</script>

<style scoped></style>
