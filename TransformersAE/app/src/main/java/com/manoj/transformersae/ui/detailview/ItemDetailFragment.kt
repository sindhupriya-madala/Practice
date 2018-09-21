package com.manoj.transformersae.ui.detailview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.manoj.transformersae.R
import com.manoj.transformersae.dummy.DummyContent
import com.manoj.transformersae.model.BotModel
import com.manoj.transformersae.ui.CriteriaComponent
import kotlinx.android.synthetic.main.activity_item_detail.*

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [MainActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: DummyContent.DummyItem? = null
    private lateinit var mCriteriaStrengthView: CriteriaComponent
    private lateinit var mCriteriaIntelligenceView: CriteriaComponent
    private lateinit var mCriteriaSpeedView: CriteriaComponent
    private lateinit var mCriteriaEnduranceView: CriteriaComponent
    private lateinit var mCriteriaRankView: CriteriaComponent
    private lateinit var mCriteriaCourageView: CriteriaComponent
    private lateinit var mCriteriaFirePowerView: CriteriaComponent
    private lateinit var mCriteriaSkillView: CriteriaComponent

    private lateinit var mBotModel: BotModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = DummyContent.ITEM_MAP[it.getString(ARG_ITEM_ID)]
                activity?.toolbar_layout?.title = item?.content
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        mCriteriaStrengthView = rootView.findViewById(R.id.strength)
        mCriteriaIntelligenceView = rootView.findViewById(R.id.intelligence)
        mCriteriaSpeedView = rootView.findViewById(R.id.speed)
        mCriteriaEnduranceView = rootView.findViewById(R.id.endurance)
        mCriteriaRankView = rootView.findViewById(R.id.rank)
        mCriteriaCourageView = rootView.findViewById(R.id.courage)
        mCriteriaFirePowerView = rootView.findViewById(R.id.firepower)
        mCriteriaSkillView = rootView.findViewById(R.id.skill)

        mCriteriaStrengthView.setValues(getString(R.string.criteria_strength), 1)
        mCriteriaIntelligenceView.setValues(getString(R.string.criteria_intelligence), 1)
        mCriteriaSpeedView.setValues(getString(R.string.criteria_speed), 4)
        mCriteriaEnduranceView.setValues(getString(R.string.criteria_endurance), 6)
        mCriteriaRankView.setValues(getString(R.string.criteria_rank), 9)
        mCriteriaCourageView.setValues(getString(R.string.criteria_courage), 5)
        mCriteriaFirePowerView.setValues(getString(R.string.criteria_firepower), 1)
        mCriteriaSkillView.setValues(getString(R.string.criteria_skill), 3)

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
