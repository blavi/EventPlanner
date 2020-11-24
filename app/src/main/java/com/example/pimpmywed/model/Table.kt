package com.example.pimpmywed.model

import com.example.pimpmywed.database.GuestsEntity
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class Table(title: String, items: List<GuestsEntity>) : ExpandableGroup<GuestsEntity>(title, items)