<template>
  <ValidationProvider
    :vid="$attrs.vid"
    :name="$attrs.vName"
    :mode="$attrs.mode || 'eager'"
    :rules="rules"
    v-slot="{ errors, valid, dirty }"
  >
    <v-text-field
      v-model="innerValue"
      :error-messages="errors"
      :success="valid && dirty"
      v-bind="$attrs"
      v-on="$listeners"
      v-bind:maxlength="inputMask.length"
      v-on:keypress="keyPress"
      v-on:blur="$emit('blur')"
      v-on:change="$emit('change')"
      v-on:click="$emit('click')"
      v-on:focus="$emit('focus')"
      v-on:keydown="$emit('keydown')"
      v-on:mousedown="$emit('mousedown')"
      v-on:mouseup="$emit('mouseup')"
    >
      <!-- pass through scoped slots -->
      <template
        v-for="(_, scopedSlotName) in $scopedSlots"
        v-slot:[scopedSlotName]="slotData"
      >
        <slot :name="scopedSlotName" v-bind="slotData" />
      </template>

      <!-- pass through normal slots -->
      <template v-for="(_, slotName) in $slots" v-slot:[slotName]>
        <slot :name="slotName" />
      </template>
    </v-text-field>
  </ValidationProvider>
</template>

<script>
import { ValidationProvider } from "vee-validate";

export default {
  name: "DmMaskedInput",
  components: {
    ValidationProvider,
  },
  props: {
    rules: {
      type: [Object, String],
      default: "",
    },
    // must be included in props
    value: {
      type: null,
    },
    options: {
      type: Object,
    },
  },
  data: () => ({
    innerValue: "",
    inputMask: "+## (###) ### ####",
  }),
  watch: {
    // Handles internal model changes.
    innerValue(newVal) {
      this.$emit("input", this.machineFormat(newVal));
    },
    // Handles external model changes.
    value(newVal) {
      this.innerValue = this.humanFormat(newVal);
    },
  },
  computed: {
    // innerValue: {
    //   get: function() {
    //     return this.humanFormat(this.value);
    //   },
    //   set: function(newValue) {
    //     this.$emit("input", this.machineFormat(newValue));
    //   }
    // }
  },
  methods: {
    humanFormat(value) {
      if (value) {
        value = this.formatValue(value, this.options.inputMask);
      } else {
        value = this.options.empty;
      }
      return value;
    },
    machineFormat(value) {
      if (value) {
        console.log("önce", value);
        console.log("önce", this.options);
        value = this.formatValue(value, this.options.outputMask);
        if (value === "") {
          value = this.options.empty;
        }
        // Apply the mask only only after filling
        if (this.options.applyAfter) {
          if (value.length !== this.options.outputMask.length) {
            value = this.options.empty;
          } else {
            // Event sended after filling the mask
            this.$emit("masked");
          }
        }
      } else {
        value = this.options.empty;
      }
      console.log("sonra", value);
      return value;
    },
    formatCpf(value, mask) {
      value = this.clearValue(value);
      let result = "";
      let count = 0;
      if (value) {
        let arrayValue = value.toString().split("");
        let arrayMask = mask.toString().split("");
        for (let i = 0; i < arrayMask.length; i++) {
          if (i < arrayValue.length + count) {
            if (arrayMask[i] === "#") {
              result = result + arrayValue[i - count];
            } else {
              result = result + arrayMask[i];
              count++;
            }
          }
        }
      }
      return result;
    },
    clearValue(value) {
      let result = "";
      if (value) {
        let arrayValue = value.toString().split("");
        for (let i = 0; i < arrayValue.length; i++) {
          if (this.isInteger(arrayValue[i])) {
            result = result + arrayValue[i];
          }
        }
      }
      return result;
    },
    formatValue(value, mask) {
      return this.formatCpf(value, mask);
    },
    keyPress($event) {
      // console.log($event.keyCode); //keyCodes value
      let keyCode = $event.keyCode ? $event.keyCode : $event.which;
      // if ((keyCode < 48 || keyCode > 57) && keyCode !== 46) {
      if (keyCode < 48 || keyCode > 57) {
        // 46 is dot
        $event.preventDefault();
      }
    },
    isInteger(value) {
      let result = false;
      if (Number.isInteger(parseInt(value))) {
        result = true;
      }
      return result;
    },
  },
  created() {
    if (this.value) {
      this.innerValue = this.humanFormat(this.value);
    }
  },
};
</script>
