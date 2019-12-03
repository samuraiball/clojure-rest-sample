(ns todo.handler.todos
    (:require [ataraxy.response :as response]
      [integrant.core :as ig]
      [todo.boundary.todos :as todos]))

(defmethod ig/init-key ::list [_ {:keys [db]}]
           (fn [_]
               (let [todos (todos/get-todos db)]
                    [::response/ok todos])))