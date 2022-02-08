<template>
  <transition name="modal-fade">
    <div class="modal-backdrop">
      <div class="modal">
        <header class="modal-header">
          <slot name="header"> Confirmation</slot>
        </header>

        <section class="modal-body">
          <slot name="body"> Do you want to delete this report ?</slot>
        </section>

        <footer class="modal-footer">
          <slot name="footer"></slot>
          <div class="col-sm-12 text-center">
            <button
              id="btnSearch"
              class="btn btn-primary btn-md center-block"
              Style="width: 100px;"
              @click="close"
            >
              Yes
            </button>
            <button
              id="btnClear"
              class="btn btn-danger btn-md center-block"
              Style="width: 100px;"
              @click="close"
            >
              Cancel
            </button>
          </div>
        </footer>
      </div>
    </div>
  </transition>
</template>

<script>
export default {
  name: "Modal",
  methods: {
    close() {
      this.$emit("close");
    },
  },
};
</script>

<style>
.modal-backdrop {
  /* position: fixed; */
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background-color: rgba(0, 0, 0, 0.3);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal {
  position: relative !important;
  background: #ffffff;
  box-shadow: 2px 2px 20px 1px;
  /* overflow-x: auto; */
  display: flex;
  flex-direction: column;
  height: 250px;
  width: 400px;
}

.modal-header,
.modal-footer {
  padding: 15px;
  display: flex;
}

.modal-header {
  position: relative;
  border-bottom: 1px solid #eeeeee;
  color: #4aae9b;
  justify-content: space-between;
}

.modal-footer {
  border-top: 1px solid #eeeeee;
  flex-direction: column;
  justify-content: flex-end;
}

.modal-body {
  position: relative;
  padding: 20px 10px;
}

.btn-close {
  position: absolute;
  top: 0;
  right: 0;
  border: none;
  font-size: 20px;
  padding: 10px;
  cursor: pointer;
  font-weight: bold;
  color: #4aae9b;
  background: transparent;
}

.btn-green-yes {
  color: white;
  background: #4aae9b;
  border: 1px solid #4aae9b;
  border-radius: 2px;
  margin-left: 172px;
}

.btn-green {
  color: white;
  background: #4aae9b;
  border: 1px solid #4aae9b;
  border-radius: 2px;
  margin-left: 300px;
}

.modal-fade-enter,
.modal-fade-leave-to {
  opacity: 0;
}

.modal-fade-enter-active,
.modal-fade-leave-active {
  transition: opacity 0.5s ease;
}

#btnSearch,
#btnClear {
  display: inline-block;
  vertical-align: top;
  color: white;
  background: #4aae9b;
  border: 1px solid #4aae9b;
  border-radius: 2px;
}
</style>
